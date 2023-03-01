package io.davinci.domain.models.network.responses

import com.fasterxml.jackson.annotation.JsonProperty

data class OpenAiChoice(
  val text: String,
  val index: Int,
  val logprobs: Any?,
  @JsonProperty("finish_reason")
  val finishReason: String
)
