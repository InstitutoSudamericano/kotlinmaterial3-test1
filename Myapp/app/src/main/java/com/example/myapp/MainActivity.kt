package com.example.myapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapp.ui.theme.Myapp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Myapp {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp() {
    var activeCard by remember { mutableStateOf<Int?>(null) }
    val names = List(21) { generateRandomName() }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        items(names.indices.toList()) { index ->
            ItemCard(name = names[index], index, activeCard) { selected ->
                // Here is where the logic changes to allow deactivating the current card
                activeCard = if (activeCard == selected) null else selected
            }
        }
    }
}

@Composable
fun ItemCard(name: String, index: Int, activeCard: Int?, onCardClicked: (Int) -> Unit) {
    val isActive = activeCard == index
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(120.dp)
            .clickable(enabled = isActive || activeCard == null) {
                onCardClicked(index)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = if (isActive) 16.dp else 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isActive) Color.LightGray else MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

private fun generateRandomName(): String {
    val firstNames = listOf("John", "Sarah", "Mike", "Linda", "James", "Patricia", "Robert", "Jennifer")
    val lastNames = listOf("Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson")
    return "${firstNames.random()} ${lastNames.random()}"
}