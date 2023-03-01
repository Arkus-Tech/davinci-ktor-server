package io.davinci.data.remote

import io.davinci.domain.models.network.responses.IdRes
import io.davinci.domain.models.network.NetworkState
import io.davinci.domain.models.network.responses.NewTrelloBoardResponse
import io.davinci.domain.models.network.responses.TrelloCardResponse
import io.davinci.domain.models.network.responses.TrelloColumnResponse
import io.davinci.domain.remote.TrelloApi
import io.davinci.utils.fetchTrelloWithAuth
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode

class TrelloApiImpl(
  private val trelloApiKey: String,
  private val client: HttpClient
) : TrelloApi {
  override suspend fun createNewBoard(name: String): NetworkState<NewTrelloBoardResponse> {
      return try {
        val trello = client.fetchTrelloWithAuth(trelloApiKey)
        val res = trello(
          HttpMethod.Post,
          "/boards",
          { parameters.append("name", name) },
          null
        )
        if (res.status == HttpStatusCode.OK)
          return NetworkState.Success(data = res.body())
        NetworkState.Error(message = "Error! Status Code ${res.status.value} \nBody: ${res.body<Any>()}")
      } catch (e: Exception) {
        NetworkState.Error(e.message ?: "Something went terribly wrong.")
      }
    }

  override suspend fun getBoardColumns(id: String): NetworkState<List<TrelloColumnResponse>> {
    return try {
      val trello = client.fetchTrelloWithAuth(trelloApiKey)
      val res = trello(
        HttpMethod.Get,
        "/boards/$id/lists",
        {},
        null
      )
      if (res.status == HttpStatusCode.OK)
        return NetworkState.Success(data = res.body())
      NetworkState.Error(message = "Error! Status Code ${res.status.value} \nBody: ${res.body<Any>()}")
    } catch (e: Exception) {
      NetworkState.Error(e.message ?: "Something went terribly wrong.")
    }
  }

  override suspend fun createTrelloCard(
    columnId: String,
    name: String,
    description: String,
  ): NetworkState<IdRes> {
    return try {
      val trello = client.fetchTrelloWithAuth(trelloApiKey)
      val res = trello(
        HttpMethod.Post,
        "/cards",
        {
          parameters.append("idList", columnId)
          parameters.append("name", name)
          parameters.append("desc", description)
        },
        null
      )
      if (res.status == HttpStatusCode.OK)
        return NetworkState.Success(data = res.body())
      NetworkState.Error(message = "Error! Status Code ${res.status.value} \nBody: ${res.body<Any>()}")
    } catch (e: Exception) {
      NetworkState.Error(e.message ?: "Something went terribly wrong.")
    }
  }

  override suspend fun createChecklist(
    cardId: String,
    name: String,
  ): NetworkState<IdRes> {
    return try {
      val trello = client.fetchTrelloWithAuth(trelloApiKey)
      val res = trello(
        HttpMethod.Post,
        "/cards/$cardId/checklists",
        { parameters.append("name", name) },
        null
      )
      if (res.status == HttpStatusCode.OK)
        return NetworkState.Success(data = res.body())
      NetworkState.Error(message = "Error! Status Code ${res.status.value} \nBody: ${res.body<Any>()}")
    } catch (e: Exception) {
      NetworkState.Error(e.message ?: "Something went terribly wrong.")
    }
  }

  override suspend fun addItemToChecklist(
    id: String,
    name: String,
  ): NetworkState<Boolean> {
    return try {
      val trello = client.fetchTrelloWithAuth(trelloApiKey)
      val res = trello(
        HttpMethod.Post,
        "/cards/$id/checklists",
        { parameters.append("name", name) },
        null
      )
      if (res.status == HttpStatusCode.OK)
        return NetworkState.Success(data = true)
      NetworkState.Error(message = "Error! Status Code ${res.status.value} \nBody: ${res.body<Any>()}")
    } catch (e: Exception) {
      NetworkState.Error(e.message ?: "Something went terribly wrong.")
    }
  }
}
