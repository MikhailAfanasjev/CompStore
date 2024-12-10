package com.example.compstore.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.linguareader.R

data class ImageItem(val imageRes: ImageVector, val description: String)

val imageItems = listOf(
    ImageItem(Icons.Default.Person, "Description 1"),
    ImageItem(Icons.Default.Person, "Description 2"),
    ImageItem(Icons.Default.Person, "Description 3")
)

@Composable
fun HomeScreen(navController: NavController) {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(modifier = Modifier.fillMaxSize()) {

                AsyncImage(
                    model = R.drawable.fone,
                    contentDescription = "Background Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(imageItems.size) { index ->
                        val item = imageItems[index]
                        Box(
                            modifier = Modifier
                                .size(150.dp)
                                .clickable {
                                    navController.navigate("details/${item.description}")
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                imageVector = item.imageRes,
                                contentDescription = item.description,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(16.dp))
                            )
                        }
                    }
                }
            }
        }
}

//@Preview(showBackground = true)
//@Composable
//fun HomeScreenPreview() {
//    rememberNavController()
//    HomeScreen()
//}