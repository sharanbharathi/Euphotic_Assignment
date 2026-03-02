package com.example.euphotic_assignment.data.model

import com.google.gson.annotations.SerializedName

data class Dish(
    val dishId: String?,
    val dishName: String?,
    val imageUrl: String?,
    val isVeg: Boolean?,
    val isPublished: Boolean?,
    @SerializedName("Time")
    val time: String?,
    val dishCategory: String?,
    val IngredientCategory: String?
)
