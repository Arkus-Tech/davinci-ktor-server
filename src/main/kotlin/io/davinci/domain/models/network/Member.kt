package io.davinci.domain.models.network

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Member(
  val id: String,
  val fullName: String,
  val username: String,
  val avatarUrl: String,
  val idBoards: List<String>
)
