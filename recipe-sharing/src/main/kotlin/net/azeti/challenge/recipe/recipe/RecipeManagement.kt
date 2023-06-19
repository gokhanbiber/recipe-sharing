package net.azeti.challenge.recipe.recipe

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

// This class assumes the Recipe's id is a Long, this can be changed if needed.
interface RecipeManagement{

    fun create(recipe: Recipe): Recipe

    fun getById(id: Long): Recipe

    fun update(id: Long, recipe: Recipe): Recipe

    fun delete(id: Long): Recipe

    fun getByUser(username: String): List<Recipe>

    fun all(): List<Recipe>

}