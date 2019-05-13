package com.javalin.swagger.examples

import com.javalin.swagger.swagger
import io.javalin.Context
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.get
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.servers.Server
import org.eclipse.jetty.http.HttpStatus


class HeroController {

    //@GET
    //@Path("/{id}")
    //@Operation(summary = "Find a hero by hero id")
    //@Parameter(`in` = ParameterIn.PATH, name = "id")
//    fun findHero(@Parameter(hidden = true) context: Context) {
//      //  val heroId = context.pathParam("id")
//        context.status(HttpStatus.OK_200)
//    }

    fun findHero(context: Context): Hero {
        return Hero(name = "Vuadô", power = "Avôa")
    }

}

fun main() {

    val jsonView = ObjectJsonView()
    val heroController = HeroController()

    val api = OpenAPI()
        .info(
            Info()
                .title("Heroes API")
                .version("0.1")
                .description("Super API of Heroes")
                .license(License().name("Apache 2.0"))
        )
        .servers(
            mutableListOf(
                Server().description("Heroes Server").url("http://localhost:7000")
            )
        )

    val app = Javalin.create()
        .enableCorsForAllOrigins()
        .swagger(api)
        .start()

    app.routes {
        get("/hero/:id") { ctx ->
            ctx.json("Ta funcionando!")
            ctx.status(HttpStatus.OK_200)
        }
        get("/hero/test") { ctx ->
            jsonView.render(ctx, heroController.findHero(ctx))
        }
    }


    //app.get("/hero/:id", HeroController().findHero)
}


class ObjectJsonView {
    fun render(ctx: Context, model: Any) {
        ctx.json(model)
    }
}