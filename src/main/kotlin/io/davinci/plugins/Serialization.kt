package io.davinci.plugins

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.serialization.jackson.jackson
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.application.*

fun Application.configureSerialization() {
  install(ContentNegotiation) {
    jackson {
      enable(SerializationFeature.INDENT_OUTPUT)
      disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }
  }
}
