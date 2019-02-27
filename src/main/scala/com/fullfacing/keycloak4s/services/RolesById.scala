package com.fullfacing.keycloak4s.services

import com.fullfacing.apollo.core.Predef.AsyncApolloResponse
import com.fullfacing.apollo.core.protocol.NoContent
import com.fullfacing.keycloak4s.SttpClient
import com.fullfacing.keycloak4s.models.{ManagementPermission, Role}

import scala.collection.immutable.Seq

object RolesById {

  private val resource = "roles-by-id"

  /**
   * id of role
   *
   * @param realm  realm name (not id!)
   * @param roleId id of role
   * @return
   */
  def fetch(realm: String, roleId: String): AsyncApolloResponse[Role] = {
    val path = Seq(realm, resource, roleId)
    SttpClient.get(path)
  }

  /**
   * Update the role
   *
   * @param realm  realm name (not id!)
   * @param roleId id of role
   * @param role
   * @return
   */
  def update(realm: String, roleId: String, role: Role): AsyncApolloResponse[NoContent] = {
    val path = Seq(realm, resource, roleId)
    SttpClient.put(role, path)
  }

  /**
   * Delete the role
   *
   * @param realm  realm name (not id!)
   * @param roleId id of role
   * @return
   */
  def delete(realm: String, roleId: String): AsyncApolloResponse[NoContent] = {
    val path = Seq(realm, resource, roleId)
    SttpClient.delete(path)
  }

  /**
   * Make the role a composite role by associating some child roles
   *
   * @param realm  realm name (not id!)
   * @param roleId id of role
   * @param role
   * @return
   */
  def addSubRoles(realm: String, roleId: String, roles: List[Role]): AsyncApolloResponse[NoContent] = {
    val path = Seq(realm, resource, roleId, "composites")
    SttpClient.post(roles, path)
  }

  /**
   * Get role’s children Returns a set of role’s children provided the role is a composite
   *
   * @param realm  realm name (not id!)
   * @param roleId id of role
   * @return
   */
  def fetchSubRoles(realm: String, roleId: String): AsyncApolloResponse[List[Role]] = {
    val path = Seq(realm, resource, roleId, "composites")
    SttpClient.get(path)
  }

  /**
   * Remove a set of roles from the role’s composite
   *
   * @param realm  realm name (not id!)
   * @param roleId id of role
   * @param roles   A set of roles to be removed
   * @return
   */
  def removeSubRoles(realm: String, roleId: String, roles: List[Role]): AsyncApolloResponse[NoContent] = {
    val path = Seq(realm, resource, roleId, "composites")
    SttpClient.delete(roles, path)
  }

  /**
   * Get client-level roles for the client that are in the role’s composite
   *
   * @param realm  realm name (not id!)
   * @param roleId id of role
   * @param client
   * @return
   */
  def getSubRoleClientLevelRoles(realm: String, roleId: String, client: String): AsyncApolloResponse[List[Role]] = {
    val path = Seq(realm, resource, roleId, "composites", "clients", client)
    SttpClient.get(path)
  }

  /**
   * Get realm-level roles that are in the role’s composite
   *
   * @param realm  realm name (not id!)
   * @param roleId id of role
   * @return
   */
  def getSubRoleRealmLevelRoles(realm: String, roleId: String): AsyncApolloResponse[List[Role]] = {
    val path = Seq(realm, resource, roleId, "composites", "realm")
    SttpClient.get(path)
  }

  /**
   * Return object stating whether role Authorization permissions have been initialized or not and a reference
   *
   * @param realm  realm name (not id!)
   * @param roleId id of role
   * @return
   */
  def authPermissionsInitialised(realm: String, roleId: String): AsyncApolloResponse[ManagementPermission] = {
    val path = Seq(realm, resource, roleId, "management", "permissions")
    SttpClient.get(path)
  }

  /**
   * TODO confirm description: Initialise role authorization permissions
   *
   * @param realm  realm name (not id!)
   * @param roleId id of role
   * @param ref    ..
   * @return
   */
  def initialiseRoleAuthPermissions(realm: String, roleId: String, ref: ManagementPermission): AsyncApolloResponse[ManagementPermission] = {
    val path = Seq(realm, resource, roleId, "management", "permissions")
    SttpClient.put(ref, path)
  }
}
