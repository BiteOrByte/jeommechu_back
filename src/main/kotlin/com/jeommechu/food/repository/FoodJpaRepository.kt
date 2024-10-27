package com.jeommechu.food.repository

import com.jeommechu.food.entity.FoodEntity
import org.springframework.data.jpa.repository.JpaRepository

interface FoodJpaRepository : JpaRepository<FoodEntity, Long>{
    fun findByCategoryIn(category: List<String>?): List<FoodEntity>
}