package com.jetbrains.handson.httpapi

import com.jetbrains.handson.httpapi.routes.registerCustomerRoutes
import com.jetbrains.handson.httpapi.routes.registerOrderRoutes
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*
import com.papsign.ktor.openapigen.APITag
import com.papsign.ktor.openapigen.OpenAPIGen
import com.papsign.ktor.openapigen.annotations.Response
import com.papsign.ktor.openapigen.route.apiRouting
import com.papsign.ktor.openapigen.route.info
import com.papsign.ktor.openapigen.route.path.normal.get
import com.papsign.ktor.openapigen.route.response.respond
import com.papsign.ktor.openapigen.route.route
import com.papsign.ktor.openapigen.route.tag
import com.papsign.ktor.openapigen.schema.namer.DefaultSchemaNamer
import com.papsign.ktor.openapigen.schema.namer.SchemaNamer
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.jackson.jackson


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    install(OpenAPIGen) {
        // basic info
        info {
            version = "0.0.1"
            title = "Test API"
            description = "The Test API"
            contact {
                name = "Support"
                email = "support@test.com"
            }
        }
        // describe the server, add as many as you want
        server("http://localhost:8080/") {
            description = "Test server"
        }
    }

    install(ContentNegotiation) {
        jackson()
    }

    routing {
        get("/openapi.json") {
            call.respond(application.openAPIGen.api.serialize())
        }
        get("/") {
            call.respondRedirect("/swagger-ui/index.html?url=/openapi.json", true)
        }
    }
    apiRouting {
    }
    registerCustomerRoutes()
    registerOrderRoutes()
}
