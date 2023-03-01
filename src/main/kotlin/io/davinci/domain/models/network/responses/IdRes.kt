package io.davinci.domain.models.network.responses

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class IdRes(
  val id: String
)
