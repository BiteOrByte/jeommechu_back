package com.jeommechu.food.domain

import com.jeommechu.food.entity.FoodEntity
import com.jeommechu.food.repository.FoodRepository

class FoodDomain(
    private val foodRepository: FoodRepository
) {
    fun getRandomFood(category: List<String>?): FoodEntity {
        val foodList = foodRepository.getFoodList(category)
        return foodList.random()
    }
}
