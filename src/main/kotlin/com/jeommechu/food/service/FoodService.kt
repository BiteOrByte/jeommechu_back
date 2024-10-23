package com.jeommechu.food.service

import com.jeommechu.food.domain.FoodDomain
import com.jeommechu.food.dto.FoodResponseDto
import org.springframework.stereotype.Service

@Service
class FoodService(private val foodDomain: FoodDomain) {

    init {
        foodDomain = new
    }

    fun getRandomFood(category: List<String>): FoodResponseDto {
        return FoodResponseDto.fromEntity(foodDomain.getRandomFood(category))
    }
}