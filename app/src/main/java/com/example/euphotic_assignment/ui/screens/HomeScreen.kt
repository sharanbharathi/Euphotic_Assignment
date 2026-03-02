package com.example.euphotic_assignment.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.euphotic_assignment.data.model.Dish
import com.example.euphotic_assignment.viewmodel.DishViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: DishViewModel = viewModel()
) {
    val dishes = viewModel.dishList
    val loading = viewModel.isLoading

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Euphotic Assignment") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            //  SEARCH BAR
            OutlinedTextField(
                value = viewModel.searchQuery,
                onValueChange = { viewModel.onSearchChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Search dishes...") },
                singleLine = true,
                shape = RoundedCornerShape(24.dp),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                }
            )

            //  CATEGORY FILTER ROW
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChip(
                        selected = viewModel.selectedCategory == null,
                        onClick = { viewModel.filterByCategory(null) },
                        label = { Text("All") }
                    )
                }

                items(viewModel.ingredientCategories) { category ->
                    FilterChip(
                        selected = viewModel.selectedCategory == category,
                        onClick = { viewModel.filterByCategory(category) },
                        label = { Text(category) }
                    )
                }
            }

            if (loading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                //  DISH GRID
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(180.dp),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(dishes) { dish ->
                        DishCard(dish = dish) {
                            val encodedName = URLEncoder.encode(dish.dishName ?: "Unknown", StandardCharsets.UTF_8.toString())
                            val encodedUrl = URLEncoder.encode(dish.imageUrl ?: "", StandardCharsets.UTF_8.toString())
                            val time = dish.time ?: "0"
                            val isVeg = dish.isVeg ?: false

                            navController.navigate("details/$encodedName/$encodedUrl/$time/$isVeg")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DishCard(
    dish: Dish,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column {
            AsyncImage(
                model = dish.imageUrl,
                contentDescription = dish.dishName,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )

            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (dish.isVeg == true) "🟢 Veg" else "🔴 Non-Veg",
                    style = MaterialTheme.typography.labelSmall
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "⏱ ${dish.time} mins",
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Text(
                text = dish.dishName ?: "No Name",
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1
            )

            Text(
                text = dish.dishCategory ?: "",
                modifier = Modifier.padding(start = 8.dp, bottom = 8.dp),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}
