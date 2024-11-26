package com.momokddi.food.repository

import com.momokddi.food.entity.FoodCategory
import com.momokddi.food.entity.FoodEntity

interface FoodRepository {
    fun getFoodList(category : MutableCollection<FoodCategory>?) : List<FoodEntity>
}