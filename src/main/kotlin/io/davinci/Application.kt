package io.davinci

import io.davinci.di.getKoinModule
import io.davinci.plugins.*
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main(args: Array<String>): Unit =
  io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
  val appModule = getKoinModule()
  install(Koin) {
    slf4jLogger()
    modules(appModule)
  }
  configureSerialization()
  configureRouting()
}
