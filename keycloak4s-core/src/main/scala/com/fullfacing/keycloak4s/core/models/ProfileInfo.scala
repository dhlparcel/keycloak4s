package com.fullfacing.keycloak4s.core.models

final case class ProfileInfo(disabledFeatures: Option[List[String]],
                             experimentalFeatures: Option[List[String]],
                             name: Option[String],
                             previewFeatures: Option[List[String]])