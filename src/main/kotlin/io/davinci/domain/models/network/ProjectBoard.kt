package io.davinci.domain.models.network

import kotlinx.serialization.Serializable

@Serializable
data class ProjectBoard(
  val projectName: String,
  val ticketList: List<Ticket>
)
