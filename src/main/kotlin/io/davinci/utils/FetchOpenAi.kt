package io.davinci.utils

import io.davinci.domain.models.network.OpenAiRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kotlinx.serialization.SerialName

suspend inline fun HttpClient.fetchOpenAi(
  openAiKey: String
): suspend (
  projectDescription: String) -> HttpResponse {
  return { projectDescription ->
    this.post("https://api.openai.com/v1/completions") {
      headers {
        append(HttpHeaders.Authorization, "Bearer $openAiKey")
      }
      contentType(ContentType.Application.Json)
      setBody(OpenAiRequest(
        model = "text-davinci-003",
        prompt = projectDescription.wrapWithProjectBoardFilter(),
        temperature = 0.9f,
        maxTokens = 300,
        topP = 1.0f,
        frequencyPenalty = 0.0f,
        presencePenalty = 0.0f
      ))
    }
  }
}
