package io.davinci.utils

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.URLBuilder

suspend inline fun HttpClient.fetchTrelloWithAuth(
  trelloApiKey: String
): suspend (
    method: HttpMethod,
    path: String,
    parameters: URLBuilder.(URLBuilder) -> Unit,
    body: Any?) -> HttpResponse {
  val trelloToken = "ATTA41a2a1d85659f66c22030fabe6620a6fe40b6fee9d1cf110c601c0c4b39062e4BF6B1783"
  return { method, path, parameters, body ->
    val url = "https://api.trello.com/1$path"
    when (method) {
      HttpMethod.Get -> {
        this.get(url) {
          headers {
            append(HttpHeaders.Authorization, "OAuth oauth_consumer_key=\"$trelloApiKey\", oauth_token=\"$trelloToken\"")
            append(HttpHeaders.ContentType, "application/json")
          }
          url {
            parameters(this, it)
          }
        }
      }
      HttpMethod.Post -> {
        this.post(url) {
          headers {
            append(HttpHeaders.Authorization, "OAuth oauth_consumer_key=\"$trelloApiKey\", oauth_token=\"$trelloToken\"")
            append(HttpHeaders.ContentType, "application/json")
          }
          url {
            parameters(this, it)
          }
          if (body != null)
            setBody(body)
        }
      }
      else -> {
        throw Error("Error: $method is not accounted for.")
      }
    }
  }
}
