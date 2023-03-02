package io.davinci.routes

import io.davinci.domain.models.network.RefineBoardRequest
import io.davinci.domain.remote.OpenAiApi
import io.davinci.utils.duringState
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import kotlinx.coroutines.runBlocking
import org.koin.ktor.ext.inject

fun Route.refineProjectBoard() {
  val openAiApi by inject<OpenAiApi>()
  post("/refine-project-board") {
    val body = call.receive<RefineBoardRequest>()

    openAiApi.refineProjectBoard(body.refinementSpec, body.oldProjectBoard).duringState(success = { data ->
      call.respond(data)
    }) { message ->
      run {
        runBlocking {
          call.respondText(message)
        }
      }
    }
  }
}
