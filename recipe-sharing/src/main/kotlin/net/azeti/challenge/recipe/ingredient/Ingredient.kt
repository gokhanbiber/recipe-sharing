package net.azeti.challenge.recipe.ingredient

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "ingredients")
data class Ingredient(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    @NotNull
    var unit: String,
    @NotNull
    var type: String,
    @NotNull
    var amount: Double,
    @NotNull
    var calorie: Int,
)
