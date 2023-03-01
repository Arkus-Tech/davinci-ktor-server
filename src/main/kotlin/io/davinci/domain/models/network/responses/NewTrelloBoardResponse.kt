package io.davinci.domain.models.network.responses

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class NewTrelloBoardResponse(
  val id: String,
  val name: String,
  val desc: String,
  val shortUrl: String
)
