package io.davinci.domain.models.network

import com.fasterxml.jackson.annotation.JsonProperty

data class OpenAiRequest(
  val model: String,
  val prompt: String,
  val temperature: Float,
  @JsonProperty("max_tokens")
  val maxTokens: Int,
  @JsonProperty("top_p")
  val topP: Float,
  @JsonProperty("frequency_penalty")
  val frequencyPenalty: Float,
  @JsonProperty("presence_penalty")
  val presencePenalty: Float
)
