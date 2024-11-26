package com.momokddi.food.controller

import com.momokddi.food.dto.FoodResponseDto
import com.momokddi.food.entity.FoodCategory
import com.momokddi.food.service.FoodService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/food")
class FoodController(private val foodService: FoodService) {

    @GetMapping
    fun getFood(@RequestParam(required = false) category : MutableCollection<FoodCategory>?) :ResponseEntity<FoodResponseDto> {
        val randomFood = foodService.getRandomFood(category)
        return ResponseEntity(randomFood, HttpStatus.OK)
    }

}