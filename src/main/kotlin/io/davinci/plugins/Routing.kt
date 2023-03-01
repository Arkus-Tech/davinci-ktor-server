package io.davinci.plugins

import io.davinci.routes.createProjectBoard
import io.davinci.routes.generateProjectBoard
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing

fun Application.configureRouting() {

  routing {
    get("/") {
      call.respondText("Hello World!")
    }
    route("/api/v1") {
      createProjectBoard()
      generateProjectBoard()
    }

  }
}
