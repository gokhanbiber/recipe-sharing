package net.azeti.challenge.recipe.recipe

import net.azeti.challenge.recipe.ingredient.Ingredient
import net.azeti.challenge.recipe.ingredient.IngredientRepository
import net.azeti.challenge.recipe.user.Registration
import net.azeti.challenge.recipe.user.Role
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest


@DataJpaTest
@AutoConfigureTestDatabase
class RecipeRepositoryTest {

    @Autowired
    private lateinit var ingredientRepository: IngredientRepository;
    @Autowired
    private lateinit var recipeRepository: RecipeRepository;
    @BeforeEach
    fun setup() {
        val u1: Registration = Registration(1L,"user@user.com", role = Role.USER, username = "user", password = "super-secret")

        val i1: Ingredient = Ingredient(1L,"pc","egg",1.0,10);
        val i2: Ingredient = Ingredient(2L,"g","milk",1.0,15);
        val i3: Ingredient = Ingredient(3L,"dash","salt",1.0,0);

        ingredientRepository.saveAll(listOf(i1,i2,i3))
    }


    @Test
    @DisplayName("add data with sa")
    fun addRecipes() {

        val i1 = ingredientRepository.findById(1).orElseThrow();
        val i2 = ingredientRepository.findById(2).orElseThrow();
        val i3 = ingredientRepository.findById(3).orElseThrow();


        val r1: Recipe = Recipe(1L,"pancake","user","test","do this do that", "1 portion", listOf(i1,i2));
        val r2: Recipe = Recipe(2L,"salted-pancake","user","test","do this do that", "1 portion", listOfNotNull(i1,i2,i3));

        Assertions.assertDoesNotThrow { recipeRepository.saveAll(listOf(r1,r2)) }
    }

}