package io.davinci.domain.models.network

import kotlinx.serialization.Serializable

@Serializable
data class Ticket(
  val title: String,
  val scope: String,
  val acceptanceCriteria: List<String>,
  val helpfulResources: List<String>
)
