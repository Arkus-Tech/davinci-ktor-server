package io.davinci.domain.models.network

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class Ticket @JsonCreator constructor(
  @JsonProperty("title") val title: String,
  @JsonProperty("scope") val scope: String,
  @JsonProperty("acceptanceCriteria") val acceptanceCriteria: List<String>,
  @JsonProperty("helpfulResources") val helpfulResources: List<String>
)
