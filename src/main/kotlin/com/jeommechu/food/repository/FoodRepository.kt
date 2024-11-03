package com.jeommechu.food.repository

import com.jeommechu.food.entity.FoodCategory
import com.jeommechu.food.entity.FoodEntity

interface FoodRepository {
    fun getFoodList(category : MutableCollection<FoodCategory>?) : List<FoodEntity>
}