package net.azeti.challenge.recipe.ingredient

import org.springframework.stereotype.Service

@Service
class IngredientHandler(private var repository: IngredientRepository): IngredientManagement {
    override fun getAll(): List<Ingredient> {
        return repository.findAll();
    }

}