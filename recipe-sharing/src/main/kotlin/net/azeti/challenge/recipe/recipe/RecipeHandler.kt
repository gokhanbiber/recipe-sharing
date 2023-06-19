package net.azeti.challenge.recipe.recipe

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.Optional;
import kotlin.NoSuchElementException

@Service
class RecipeHandler(private val repository: RecipeRepository) : RecipeManagement,RecipeSearch{
    @Transactional
    override fun create(recipe: Recipe): Recipe {
        return repository.save(recipe);
    }

    override fun getById(id: Long): Recipe {
        return repository.findById(id).orElseThrow { NoSuchElementException("Recipe is not found") } ;
    }

    @Transactional
    override fun update(id: Long, recipe: Recipe): Recipe {
        if (repository.existsById(id)) {
            recipe.id = id;
            return repository.save(recipe);
        }
        throw NoSuchElementException("Recipe is not found")
    }

    @Transactional
    override fun delete(id: Long): Recipe {

        var recipe = repository.findById(id);

        if(recipe.isPresent){
            repository.delete(recipe.get());
            return recipe.get();
        }
        throw NoSuchElementException("There is no such recipe")
    }

    override fun getByUser(username: String): List<Recipe> {
        return repository.findByUserName(username);
    }

    override fun all(): List<Recipe> {
        return repository.findAll();
    }

    override fun recipesByUsername(userName: String): List<Recipe> {
        return repository.findByUserNameLike(userName)
    }

    override fun recipesByTitle(title: String): List<Recipe> {
        return repository.findByTitleLike(title);
    }

}