package com.example.euphotic_assignment.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.euphotic_assignment.ui.screens.HomeScreen
import com.example.euphotic_assignment.ui.screens.DetailsScreen
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun AppNavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {
            HomeScreen(navController = navController)
        }

        composable(
            route = "details/{dishName}/{imageUrl}/{time}/{isVeg}",
            arguments = listOf(
                navArgument("dishName") { type = NavType.StringType },
                navArgument("imageUrl") { type = NavType.StringType },
                navArgument("time") { type = NavType.StringType },
                navArgument("isVeg") { type = NavType.BoolType }
            )
        ) { backStackEntry ->
            val encodedDishName = backStackEntry.arguments?.getString("dishName") ?: ""
            val encodedImageUrl = backStackEntry.arguments?.getString("imageUrl") ?: ""
            
            // DECODE the values to remove '+' and special characters
            val dishName = URLDecoder.decode(encodedDishName, StandardCharsets.UTF_8.toString())
            val imageUrl = URLDecoder.decode(encodedImageUrl, StandardCharsets.UTF_8.toString())

            val time = backStackEntry.arguments?.getString("time") ?: ""
            val isVeg = backStackEntry.arguments?.getBoolean("isVeg") ?: false

            DetailsScreen(
                dishName = dishName,
                imageUrl = imageUrl,
                time = time,
                isVeg = isVeg,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
