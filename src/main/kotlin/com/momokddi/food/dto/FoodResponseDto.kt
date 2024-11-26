package com.momokddi.food.dto

import com.momokddi.food.entity.FoodCategory

data class FoodResponseDto(
    val name: String,
    val category: FoodCategory
)