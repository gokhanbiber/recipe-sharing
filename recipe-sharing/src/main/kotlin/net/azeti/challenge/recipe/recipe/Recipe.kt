package net.azeti.challenge.recipe.recipe

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import net.azeti.challenge.recipe.ingredient.Ingredient

@Entity
@Table(name = "recipes")
data class Recipe(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    @NotNull
    var title: String,
    @NotNull
    var userName: String,
    var description: String,
    @NotNull
    var instruction: String,
    var serving: String,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "recipe_ingredient",
        joinColumns = [JoinColumn(name = "recipe_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "ingredient_id", referencedColumnName = "id")]
    )
    var ingredients: List<Ingredient>)
