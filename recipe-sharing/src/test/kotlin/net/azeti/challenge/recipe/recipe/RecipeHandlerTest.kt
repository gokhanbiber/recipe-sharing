package net.azeti.challenge.recipe.recipe

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*
import kotlin.NoSuchElementException


@ExtendWith(MockitoExtension::class)
class RecipeHandlerTest {

    @InjectMocks
    private lateinit var recipeHandler: RecipeHandler

    @Mock
    private lateinit var recipeRepository: RecipeRepository;

    @Test
    @DisplayName(value = "Get by Id test with mockito extension")
    fun getById() {
        Mockito.`when`(recipeRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException::class.java) { recipeHandler.getById(1L) }

    }
}