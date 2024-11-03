package com.jeommechu.food.repository

import com.jeommechu.food.entity.FoodCategory
import com.jeommechu.food.entity.FoodEntity
import org.springframework.stereotype.Repository

@Repository
class FoodRepositoryImpl(private val foodJpaRepository: FoodJpaRepository) : FoodRepository {
    override fun getFoodList(category: MutableCollection<FoodCategory>?): List<FoodEntity> {
        return if (category.isNullOrEmpty()) (foodJpaRepository.findAll())
        else (foodJpaRepository.findByCategoryIn(category))
    }
}