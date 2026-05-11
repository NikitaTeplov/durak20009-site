package com.nikitateplov

import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.engine.embeddedServer
import io.ktor.server.http.content.staticResources
import io.ktor.server.netty.Netty
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun main() {
    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080

    embeddedServer(
        factory = Netty,
        port = port,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    routing {
        get("/") {
            val page = this::class.java.classLoader.getResource("static/index.html")?.readText()

            if (page == null) {
                call.respondText("index.html not found", status = HttpStatusCode.NotFound)
            } else {
                call.respondText(page, ContentType.Text.Html)
            }
        }

        staticResources("/", "static")
    }
}
