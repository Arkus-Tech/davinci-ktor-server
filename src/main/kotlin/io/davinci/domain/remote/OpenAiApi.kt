package io.davinci.domain.remote

import io.davinci.domain.models.network.NetworkState
import io.davinci.domain.models.network.ProjectBoard

interface OpenAiApi {
  suspend fun generateProjectBoard(projectDescription: String): NetworkState<ProjectBoard>
  suspend fun refineProjectBoard(refinementSpec: String, oldProjectBoard: ProjectBoard): NetworkState<ProjectBoard>
}
