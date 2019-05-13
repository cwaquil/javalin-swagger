package com.javalin.swagger

import com.javalin.swagger.examples.Hero
import io.javalin.Javalin
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.PathItem
import io.swagger.v3.oas.models.media.Content
import io.swagger.v3.oas.models.media.MediaType
import io.swagger.v3.oas.models.media.Schema
import io.swagger.v3.oas.models.parameters.Parameter
import io.swagger.v3.oas.models.responses.ApiResponse
import io.swagger.v3.oas.models.responses.ApiResponses

fun Javalin.swagger(
    openApi: OpenAPI,
    enable: Boolean = true,
    enableUiEndpoint: Boolean = true,
    enableJsonEndpoint: Boolean = true,
    apiFileName: String = "openapi.json",
    swaggerUIendPoint: String = "swagger-ui"
): Javalin {

    val resp = ApiResponse()
    resp.description = "resposta padrao"
    resp.content = Content().addMediaType("json", MediaType())

    val resps = ApiResponses().addApiResponse("respI", resp)

    val schema = Schema<Hero>()

    val param = Parameter()
    param.name = "id"
    param.`in` = "path"
    param.required = true
    param.schema = schema

    val operation = Operation()
    operation.tags(arrayListOf("Heroes API v0.0.1"))
    operation.summary("Find a hero by hero id")
    operation.operationId("findHero")
    operation.parameters(listOf(param))
    operation.responses(resps)

    val pathItem = PathItem()
    pathItem.get(operation)

    openApi.path(
        "/herohhh", pathItem
    )

    if (enable) {

        this.enableWebJars()

        this.routes {

            if (enableJsonEndpoint) {
                get(apiFileName) {
                    it.result(openApi.toJson()).contentType("application/json")
                }
            }

            if (enableUiEndpoint) {
                get(swaggerUIendPoint, SwaggerRenderer("/$apiFileName"))
            }

        }

    }

    return this
}


