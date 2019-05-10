package com.fullfacing.keycloak4s.core.models

final case class AdminEvent(authDetails: Option[AuthDetails],
                            error: Option[String],
                            operationType: Option[String],
                            realmId: Option[String],
                            representation: Option[String],
                            resourcePath: Option[String],
                            resourceType: Option[String],
                            time: Option[Long])