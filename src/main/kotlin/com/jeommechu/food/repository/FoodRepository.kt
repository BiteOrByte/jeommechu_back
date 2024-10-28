package com.jeommechu.food.repository

import com.jeommechu.food.entity.FoodEntity

interface FoodRepository {
    fun getFoodList(category : List<String>?) : List<FoodEntity>
}