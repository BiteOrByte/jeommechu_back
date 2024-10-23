package com.jeommechu.food

import com.jeommechu.food.domain.Food
import com.jeommechu.food.repository.FoodRepository
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class FoodTests {

    @Autowired
    private lateinit var foodRepository: FoodRepository

    @Test
    fun `getRandomFood Test`() {
        // given
        val food = Food(foodRepository);

        // when
        val result = food.getRandomFood(null);

        println(result)

        // then
        assertThat(result.name).isNotNull
        assertThat(result.category).isNotNull

    }
}