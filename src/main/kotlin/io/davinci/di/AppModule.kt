package io.davinci.di

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.davinci.data.remote.OpenAiApiImpl
import org.koin.core.module.Module
import io.davinci.data.remote.TrelloApiImpl
import io.davinci.domain.remote.OpenAiApi
import io.davinci.domain.remote.TrelloApi
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.jackson.jackson
import io.ktor.server.application.Application
import org.koin.dsl.module

fun Application.getKoinModule(): Module {
  val client = HttpClient(CIO) {
    install(ContentNegotiation) {
      jackson {
        disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
      }
    }
    install(Logging)
//    install(HttpTimeout) {
//      requestTimeoutMillis = 120000
//    }
  }
  val trelloApiKey = environment.config.propertyOrNull("ktor.secrets.trello")?.getString() ?: ""
  val openAiApiKey = environment.config.propertyOrNull("ktor.secrets.openai")?.getString() ?: ""
  return module {
    single<TrelloApi> { TrelloApiImpl(trelloApiKey, client) }
    single<OpenAiApi> { OpenAiApiImpl(openAiApiKey, client) }
  }
}
