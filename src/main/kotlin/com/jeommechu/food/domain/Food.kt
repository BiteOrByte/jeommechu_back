package com.jeommechu.food.domain

import com.jeommechu.food.entity.FoodEntity
import com.jeommechu.food.repository.FoodRepository

class Food(private val foodRepository: FoodRepository) {

    fun getRandomFood(categories: List<String>?): FoodEntity {
        val foods = if (categories?.isNotEmpty() == true) {
            // 주어진 카테고리에 해당하는 음식 목록을 조회
            foodRepository.findByCategoryIn(categories)
        } else {
            // 카테고리가 비어있으면 모든 음식 목록을 조회
            foodRepository.findAll()
        }

        // 조회된 음식이 존재하면 랜덤으로 하나 선택
        return foods.random() // Kotlin의 random() 함수를 사용
    }
}
