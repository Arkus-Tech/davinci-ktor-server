package io.davinci.routes

import io.davinci.domain.models.network.ProjectBoard
import io.davinci.domain.remote.TrelloApi
import io.davinci.utils.duringState
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import kotlinx.coroutines.runBlocking
import org.koin.ktor.ext.inject

fun Route.createProjectBoard() {
  val trelloApi by inject<TrelloApi>()
  post("/create-trello-project") {
    val body = call.receive<ProjectBoard>()
    runBlocking {
      val newBoardId = trelloApi.createNewBoard(body.projectName).duringState(success = { data ->
        data.id
      }, error = { message -> throw Error(message)})

      val columnId = trelloApi.getBoardColumns(newBoardId).duringState(success = { data ->
        data.first().id
      }, error = { message -> throw Error(message) })

      for (ticket in body.ticketList) {
        val cardId = trelloApi.createTrelloCard(columnId, ticket.title, ticket.scope).duringState(success = { data ->
          data.id
        }, error = { message -> throw Error(message)})

        val checklistId = trelloApi.createChecklist(cardId, "Acceptance Criteria").duringState(success = { data ->
         data.id
        }, error = { message -> throw Error(message)})

        for (ac in ticket.acceptanceCriteria) {
          trelloApi.addItemToChecklist(checklistId, ac)
        }
      }

      call.respondText("Success!!!")
    }
  }
}
