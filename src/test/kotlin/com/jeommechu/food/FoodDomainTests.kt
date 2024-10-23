package com.jeommechu.food

import com.jeommechu.food.domain.FoodDomain
import com.jeommechu.food.repository.FoodRepository
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class FoodDomainTests {

    @Autowired
    private lateinit var foodRepository: FoodRepository

    @Test
    fun `getRandomFood Test`() {
        // given
        val foodDomain = FoodDomain(foodRepository);

        // when
        val result = foodDomain.getRandomFood(null);

        println(result)

        // then
        assertThat(result.name).isNotNull
        assertThat(result.category).isNotNull

    }
}