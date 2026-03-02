package com.example.euphotic_assignment.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.*
import com.example.euphotic_assignment.data.model.Dish
import com.example.euphotic_assignment.data.model.repository.DishRepository

class DishViewModel : ViewModel() {

    private val repository = DishRepository()

    var dishList by mutableStateOf<List<Dish>>(emptyList())
        private set

    var isLoading by mutableStateOf(true)
        private set

    private var fullList: List<Dish> = emptyList()

    var selectedCategory by mutableStateOf<String?>(null)
        private set

    var ingredientCategories by mutableStateOf<List<String>>(emptyList())
        private set

    var searchQuery by mutableStateOf("")
        private set

    init {
        fetchDishes()
    }

    fun filterByCategory(category: String?) {
        selectedCategory = category
        applyFilters()
    }

    fun onSearchChange(query: String) {
        searchQuery = query
        applyFilters()
    }

    private fun applyFilters() {
        dishList = fullList.filter { dish ->
            val matchesCategory = selectedCategory == null || dish.IngredientCategory == selectedCategory
            val matchesSearch = searchQuery.isBlank() || dish.dishName?.contains(searchQuery, ignoreCase = true) == true
            matchesCategory && matchesSearch
        }
    }

    private fun fetchDishes() {
        viewModelScope.launch {
            isLoading = true
            try {
                val result = repository.getDishes()
                Log.d("DishViewModel", "Fetched ${result.size} dishes")
                
                fullList = result
                dishList = result

                ingredientCategories = result
                    .mapNotNull { it.IngredientCategory }
                    .distinct()

            } catch (e: Exception) {
                Log.e("DishViewModel", "Error fetching dishes: ${e.message}", e)
            } finally {
                isLoading = false
            }
        }
    }
}
