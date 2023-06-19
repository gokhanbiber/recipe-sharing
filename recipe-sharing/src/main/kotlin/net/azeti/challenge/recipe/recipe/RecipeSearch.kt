package net.azeti.challenge.recipe.recipe

interface RecipeSearch {

    fun recipesByUsername(userName: String): List<Recipe>

    fun recipesByTitle(title: String): List<Recipe>
}