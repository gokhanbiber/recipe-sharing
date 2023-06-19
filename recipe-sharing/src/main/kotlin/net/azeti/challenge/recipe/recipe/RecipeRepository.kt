package net.azeti.challenge.recipe.recipe

import org.springframework.data.jpa.repository.JpaRepository

interface RecipeRepository : JpaRepository<Recipe, Long> {
    fun findByUserName(userName: String): List<Recipe>;
    fun findByUserNameLike(userName: String): List<Recipe>;
    fun findByTitleLike(title: String): List<Recipe>;
}