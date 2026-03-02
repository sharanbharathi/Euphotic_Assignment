package com.example.euphotic_assignment.data.model.repository

import com.example.euphotic_assignment.data.model.Dish
import com.example.euphotic_assignment.data.model.network.RetrofitInstance

class DishRepository {

    suspend fun getDishes(): List<Dish> {
        val response = RetrofitInstance.api.getDishes()
        println("API RESPONSE: $response")
        return response
    }
}