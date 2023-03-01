package io.davinci.domain.models.network

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper

data class ProjectBoard @JsonCreator constructor(
  @JsonProperty("projectName") val projectName: String,
  @JsonProperty("ticketList") val ticketList: List<Ticket>
) {
  constructor(jsonString: String) : this(
    ObjectMapper().readTree(jsonString)["projectName"].asText(),
    ObjectMapper().readTree(jsonString)["ticketList"].map { ObjectMapper().treeToValue(it, Ticket::class.java) }
  )
}
