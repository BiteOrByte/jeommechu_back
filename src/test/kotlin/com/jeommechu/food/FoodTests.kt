package com.jeommechu.food

import com.jeommechu.food.entity.FoodCategory
import com.jeommechu.food.service.FoodService
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class FoodTests {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var foodService: FoodService

    @Test
    fun `test get random food`() {
        val category = mutableListOf(FoodCategory.양식)
        val randomFood = foodService.getRandomFood(category)
        println("randomFood = $randomFood")
        assertThat(randomFood).isNotNull
    }

    @Test
    fun `test get food without category`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/food")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").isNotEmpty)
            .andExpect(MockMvcResultMatchers.jsonPath("$.category").isNotEmpty)
    }

    @Test
    fun `test get food with category`() {
        val categories = listOf(FoodCategory.한식)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/food")
                .param("category", categories.joinToString(",") { it.name })
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").isNotEmpty)
            .andExpect(MockMvcResultMatchers.jsonPath("$.category").value(FoodCategory.한식.name))
    }
}