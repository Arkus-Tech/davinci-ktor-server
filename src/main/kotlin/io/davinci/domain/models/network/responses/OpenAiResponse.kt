package io.davinci.domain.models.network.responses

import com.fasterxml.jackson.annotation.JsonProperty

data class OpenAiResponse(
  val id: String,
  @JsonProperty("object")
  val typeObject: String,
  val created: Long,
  val model: String,
  val choices: List<OpenAiChoice>,
  val usage: OpenAiUsage
)
