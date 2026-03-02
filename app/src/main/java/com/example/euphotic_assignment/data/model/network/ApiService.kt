package com.example.euphotic_assignment.data.model.network

import com.example.euphotic_assignment.data.model.Dish
//import com.example.euphotic_assignment.data.model.DishResponse
import retrofit2.http.GET

interface ApiService {
    @GET("dev/nosh-assignment")
    suspend fun getDishes(): List<Dish>
}