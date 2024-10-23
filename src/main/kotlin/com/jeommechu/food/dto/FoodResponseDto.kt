package com.jeommechu.food.dto

import com.jeommechu.food.entity.FoodCategory
import com.jeommechu.food.entity.FoodEntity

data class FoodResponseDto(
    val seq: Long,
    val name: String,
    val category: FoodCategory
) {
    companion object {
        // FoodEntity를 FoodDto로 변환하는 함수
        fun fromEntity(entity: FoodEntity): FoodResponseDto {
            return FoodResponseDto(
                seq = entity.seq ?: throw IllegalArgumentException("ID must not be null"), // ID는 null이 아님
                name = entity.name,
                category = entity.category
            )
        }
    }
}
