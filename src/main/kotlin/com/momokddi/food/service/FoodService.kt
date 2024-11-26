package com.momokddi.food.service

import com.momokddi.food.dto.FoodResponseDto
import com.momokddi.food.entity.FoodCategory
import com.momokddi.food.entity.FoodEntity
import com.momokddi.food.repository.FoodRepository
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