package io.davinci.domain.remote

import io.davinci.domain.models.network.responses.IdRes
import io.davinci.domain.models.network.NetworkState
import io.davinci.domain.models.network.responses.NewTrelloBoardResponse
import io.davinci.domain.models.network.responses.TrelloCardResponse
import io.davinci.domain.models.network.responses.TrelloColumnResponse

interface TrelloApi {
  suspend fun createNewBoard(name: String): NetworkState<NewTrelloBoardResponse>
  suspend fun getBoardColumns(id: String): NetworkState<List<TrelloColumnResponse>>
  suspend fun createTrelloCard(columnId: String, name: String, description: String): NetworkState<IdRes>
  suspend fun createChecklist(cardId: String, name: String): NetworkState<IdRes>
  suspend fun addItemToChecklist(id: String, name: String): NetworkState<Boolean>
}
