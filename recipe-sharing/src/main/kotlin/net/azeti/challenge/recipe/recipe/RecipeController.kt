package net.azeti.challenge.recipe.recipe

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@Tag(name = "Recipe", description = "Recipe management APIs")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("api/v1/recipe")
class RecipeController(private var handlerManager:RecipeManagement, private var handlerSearch:RecipeSearch) {

    @Operation(
            summary = "Retrieve all recipes",
            description = "This is created to check all recipes.")
    @ApiResponses(
            value = [
                ApiResponse(responseCode = "200", description = "Successful Operation"),
            ]
    )
    @GetMapping("all")
    fun getRecipes(): ResponseEntity<List<Recipe>> {

        return ResponseEntity
                .ok()
                .body(handlerManager.all());
    }

    @Operation(
            summary = "Retrieve all recipes created by username/title.",
            description = "Searching recipe created by any user")
    @ApiResponses(
            value = [
                ApiResponse(responseCode = "200", description = "Successful Operation"),
                ApiResponse(responseCode = "400", description = "Bad Request"),

            ]
    )
    @GetMapping
    fun getRecipes(@RequestParam(name = "username", required = false) username:String?,
                   @RequestParam(name = "title", required = false) title:String?): ResponseEntity<List<Recipe>> {

        return ResponseEntity
                .ok()
                .body(dispatchSearch(username,title));
    }

    @PostMapping
    fun addRecipe(@RequestBody recipe:Recipe): ResponseEntity<Recipe> {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(handlerManager.create(recipe));
    }
    @GetMapping("{id}")
    fun getRecipeById(@PathVariable id:Long): ResponseEntity<Recipe> {
        return ResponseEntity
                .ok()
                .body(handlerManager.getById(id));
    }
    @GetMapping("username/{username}")
    fun getRecipeById(@PathVariable username: String): ResponseEntity<List<Recipe>> {
        return ResponseEntity
                .ok()
                .body(handlerManager.getByUser(username));
    }
    @PutMapping("{id}")
    fun addRecipe(@PathVariable id:Long, @RequestBody recipe:Recipe): ResponseEntity<Recipe> {
        return ResponseEntity
                .ok()
                .body(handlerManager.update(id,recipe));
    }
    @DeleteMapping("{id}")
    fun addRecipe(@PathVariable id:Long): ResponseEntity<Void> {
        handlerManager.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    fun dispatchSearch(username:String?,title:String?): List<Recipe>{
        return if (username != null) handlerSearch.recipesByUsername(username) else handlerSearch.recipesByTitle(title!!);
    }
}