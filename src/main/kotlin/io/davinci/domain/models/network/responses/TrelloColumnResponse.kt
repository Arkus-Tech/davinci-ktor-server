package io.davinci.domain.models.network.responses

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class TrelloColumnResponse(
  val id: String,
  val name: String,
  val idBoard: String,
  val pos: Int,
  val softLimit: Int?
)
