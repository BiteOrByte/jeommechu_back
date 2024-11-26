package com.momokddi.food.repository

import com.momokddi.food.entity.FoodCategory
import com.momokddi.food.entity.FoodEntity
import org.springframework.data.jpa.repository.JpaRepository

interface FoodJpaRepository : JpaRepository<FoodEntity, Long>{
    fun findByCategoryIn(category: MutableCollection<FoodCategory>?): List<FoodEntity>
}