package com.momokddi.food.entity

import jakarta.persistence.*

@Entity
@Table(name = "foods")
data class FoodEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val seq: Long? = null,

    val name: String,

    @Enumerated(EnumType.STRING)
    val category: FoodCategory
)
