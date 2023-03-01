package io.davinci.data.remote

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.davinci.domain.models.network.NetworkState
import io.davinci.domain.models.network.ProjectBoard
import io.davinci.domain.models.network.responses.OpenAiResponse
import io.davinci.domain.remote.OpenAiApi
import io.davinci.utils.fetchOpenAi
import io.davinci.utils.fetchTrelloWithAuth
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode

class OpenAiApiImpl(
  private val openAiApiKey: String,
  private val client: HttpClient
) : OpenAiApi {
  override suspend fun generateProjectBoard(projectDescription: String): NetworkState<ProjectBoard> {

    return try {
      val openai = client.fetchOpenAi(openAiApiKey)
      val res = openai(
        projectDescription
      )
      val body = res.body<OpenAiResponse>()
      val objectMapper = ObjectMapper().registerKotlinModule()
      if (res.status == HttpStatusCode.OK)
        return NetworkState.Success(data = objectMapper.readValue(body.choices[0].text, ProjectBoard::class.java))
      NetworkState.Error(message = "Error! Status Code ${res.status.value} \nBody: ${res.body<String>()}")
    } catch (e: Exception) {
      NetworkState.Error(e.message ?: "Something went terribly wrong.")
    }
  }
}
