package com.fullfacing.keycloak4s.auth.akka.http.models.node

import akka.http.scaladsl.model.HttpMethod
import com.fullfacing.keycloak4s.auth.akka.http.models.common.PolicyEnforcement
import com.fullfacing.keycloak4s.auth.akka.http.models.{Evaluation, Result}
import com.fullfacing.keycloak4s.core.models.enums.PolicyEnforcementMode

trait Node extends PolicyEnforcement {

  val nodes: List[ResourceNode]
  val enforcementMode: PolicyEnforcementMode

  /** Looks for a wildcard role configured for this node */
  def evaluateWildcardRole(method: HttpMethod, userRoles: List[String]): Boolean = {
    nodes.find(_.segment == "*").exists { node =>
      val hasWildcardRole    = node.evaluateWildcardMethodsRole(userRoles)

      lazy val hasMethodRole = node.roles.find(_.method.value == method.value) match {
        case None    => noMatchingPolicy()
        case Some(r) => r.evaluateUserAccess(userRoles)
      }

      hasWildcardRole || hasMethodRole
    }
  }

  /**
   * Determines user's access to the given segment.
   * A check is done to see if there is an admin role configured for this resource, and immediately accepts the request
   * if the user has that role. Otherwise a search is done for the roles configured for the HTTP method
   * of the request and either denies the request or allows evaluation to continue further down the request path.
   *
   * @param segment   The resource/action the user is trying to access.
   * @param method    The HTTP method of the request.
   * @param userRoles The user's permissions
   */
  def evaluateSecurityPolicy(segment: String, method: HttpMethod, userRoles: List[String]): Evaluation[ResourceNode] = {
    if (evaluateWildcardRole(method, userRoles)) {
      Result(true)
    } else {
      nodes.find(_.segment == segment) match {
        case None       => Result(noMatchingPolicy())
        case Some(node) => if (node.policyDisabled()) Result(true) else node.evaluate(method, userRoles)
      }
    }
  }
}