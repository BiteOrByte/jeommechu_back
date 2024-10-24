package com.jeommechu.food.service

import com.jeommechu.food.domain.FoodDomain
import com.jeommechu.food.dto.FoodResponseDto
import com.jeommechu.food.repository.FoodRepository
import org.springframework.stereotype.Service

@Service
class FoodService(private val foodRepository: FoodRepository) {

    private val foodDomain = FoodDomain(foodRepository);

    fun getRandomFood(category: List<String>?): FoodResponseDto {
        return FoodResponseDto.fromEntity(foodDomain.getRandomFood(category))
    }
}