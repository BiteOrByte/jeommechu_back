package com.jeommechu.food.controller

import com.jeommechu.food.dto.FoodResponseDto
import com.jeommechu.food.service.FoodService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/food")
class FoodController(private val foodService: FoodService) {

    @GetMapping
    fun getFood(@RequestParam(required = false) category : List<String>?) :ResponseEntity<FoodResponseDto> {
        val randomFood = foodService.getRandomFood(category)
        return ResponseEntity(randomFood, HttpStatus.OK)
    }

}