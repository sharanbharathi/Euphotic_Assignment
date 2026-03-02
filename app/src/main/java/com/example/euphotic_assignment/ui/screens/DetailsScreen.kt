package com.example.euphotic_assignment.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    dishName: String,
    imageUrl: String,
    time: String,
    isVeg: Boolean,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dish Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //  HERO IMAGE
            AsyncImage(
                model = imageUrl,
                contentDescription = dishName,
                modifier = Modifier
                    .width(700.dp)
                    .height(450.dp)
                    .clip(RoundedCornerShape(32.dp)),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                //  CATEGORY & TIME TAGS
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SuggestionChip(
                        onClick = { },
                        label = { Text(if (isVeg) "Veg" else "Non-Veg") },
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            labelColor = if (isVeg) Color(0xFF2E7D32) else Color(0xFFC62828)
                        )
                    )

                    AssistChip(
                        onClick = { },
                        label = { Text("$time Mins") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Info, // Temporarily using Info until Gradle Sync finishes
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                //  DISH NAME
                Text(
                    text = dishName,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

                //  DESCRIPTION PLACEHOLDER
                Text(
                    text = "About this dish",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Enjoy this delicious $dishName prepared with fresh ingredients. This dish is perfect for a healthy meal and takes approximately $time minutes to prepare. Experience authentic flavors in every bite.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.2
                )

                Spacer(modifier = Modifier.height(32.dp))

                //  ACTION BUTTON
                Button(
                    onClick = { /* Handle Order */ },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    Text("Order Now", style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}
