package net.azeti.challenge.recipe.ingredient

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Ingredient", description = "Ingredient management APIs")
@RestController
@RequestMapping("api/v1/ingredient")
class IngredientController(private var handler: IngredientHandler) {

    @Operation(
            summary = "Retrieve all ingredients",
            description = "This is created to check all ingredients.")
    @ApiResponses(
            value = [
                ApiResponse(responseCode = "200", description = "Successful Operation"),
            ]
    )
    @GetMapping
    fun getAll(): List<Ingredient> {
        return handler.getAll();
    }
}