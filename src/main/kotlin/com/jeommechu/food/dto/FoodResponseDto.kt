package com.jeommechu.food.dto

import com.jeommechu.food.entity.FoodCategory
import com.jeommechu.food.entity.FoodEntity

data class FoodResponseDto(
    val name: String,
    val category: FoodCategory
)