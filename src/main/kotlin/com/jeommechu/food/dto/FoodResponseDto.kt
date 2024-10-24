package com.jeommechu.food.dto

import com.jeommechu.food.entity.FoodCategory
import com.jeommechu.food.entity.FoodEntity

data class FoodResponseDto(
    val name: String,
    val category: FoodCategory
) {
    companion object {
        // FoodEntity를 FoodDto로 변환하는 함수
        fun fromEntity(entity: FoodEntity): FoodResponseDto {
            return FoodResponseDto(
                name = entity.name,
                category = entity.category
            )
        }
    }
}
