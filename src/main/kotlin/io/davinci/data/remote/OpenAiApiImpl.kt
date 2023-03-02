package io.davinci.data.remote

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.davinci.domain.models.network.NetworkState
import io.davinci.domain.models.network.ProjectBoard
import io.davinci.domain.models.network.responses.OpenAiResponse
import io.davinci.domain.remote.OpenAiApi
import io.davinci.utils.fetchOpenAi
import io.davinci.utils.wrapWithProjectBoardFilter
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

class OpenAiApiImpl(
  private val openAiApiKey: String,
  private val client: HttpClient
) : OpenAiApi {
  override suspend fun generateProjectBoard(projectDescription: String): NetworkState<ProjectBoard> {
    return try {
      val openai = client.fetchOpenAi(openAiApiKey)
      return openai(
        projectDescription.wrapWithProjectBoardFilter()
      ).returnProjectBoard()
    } catch (e: Exception) {
      NetworkState.Error(e.message ?: "Something went terribly wrong.")
    }
  }

  override suspend fun refineProjectBoard(refinementSpec: String, oldProjectBoard: ProjectBoard): NetworkState<ProjectBoard> {
    return try {
      val openai = client.fetchOpenAi(openAiApiKey)
      return openai(
        "The following is my project board: $oldProjectBoard. I would like to refine it by: $refinementSpec" +
        "Respond in json format with object keys wrapped in double quotes with escape characters. Please generate a new " +
        "project board according to the new specs."
      ).returnProjectBoard()
    } catch (e: Exception) {
      NetworkState.Error(e.message ?: "Something went terribly wrong.")
    }
  }
}

suspend fun HttpResponse.returnProjectBoard(): NetworkState<ProjectBoard> {
  val body = this.body<OpenAiResponse>()
  val objectMapper = ObjectMapper().registerKotlinModule()
  val projectBoard = objectMapper.readValue(body.choices[0].text.trim(), ProjectBoard::class.java)

  return if (this.status == HttpStatusCode.OK)
    NetworkState.Success(data = projectBoard)
  else
    NetworkState.Error(message = "Error! Status Code ${this.status.value} \nBody: ${this.body<String>()}")
}
