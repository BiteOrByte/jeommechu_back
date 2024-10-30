package com.jeommechu.food.service

import com.jeommechu.food.dto.FoodResponseDto
import com.jeommechu.food.entity.FoodCategory
import com.jeommechu.food.entity.FoodEntity
import com.jeommechu.food.repository.FoodRepository
import org.springframework.stereotype.Service

@Service
class FoodService(private val foodRepository: FoodRepository) {

    fun getRandomFood(category: MutableCollection<FoodCategory>?): FoodResponseDto {
        val foods = foodRepository.getFoodList(category)
        return toResponseDto(foods.random())
    }

    // 필요시 작성
    fun toEntity() {}

    fun toResponseDto(food : FoodEntity): FoodResponseDto {
        return FoodResponseDto(name = food.name, category = food.category)
    }
}