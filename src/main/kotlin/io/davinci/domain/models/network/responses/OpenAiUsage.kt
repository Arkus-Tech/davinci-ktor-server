package io.davinci.domain.models.network.responses

import com.fasterxml.jackson.annotation.JsonProperty

data class OpenAiUsage(
  @JsonProperty("prompt_tokens")
  val promptTokens: Int,
  @JsonProperty("completion_tokens")
  val completionTokens: Int,
  @JsonProperty("total_tokens")
  val totalTokens: Int
)
