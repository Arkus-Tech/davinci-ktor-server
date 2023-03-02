package io.davinci.domain.models.network

data class RefineBoardRequest(
  val refinementSpec: String,
  val oldProjectBoard: ProjectBoard
)
