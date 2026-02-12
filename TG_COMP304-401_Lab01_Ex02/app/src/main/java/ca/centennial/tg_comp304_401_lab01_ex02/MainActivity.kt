// Tristan G - 301374901 - 2026-02-06
// COMP 304 - Lab Assignment 01 - Exercise 02 - Phone Product List App

package ca.centennial.tg_comp304_401_lab01_ex02

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ca.centennial.tg_comp304_401_lab01_ex02.ui.theme.TG_COMP304401_Lab01_Ex02Theme
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TG_COMP304401_Lab01_Ex02Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ProductList()

                }
            }
        }
    }

    data class Product(
        val id: Int,
        val name: String,
        val price: Double,
        val manufacturer: String,
        val launchDate: LocalDate,
        val imageId: Int
    )

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ProductList() {

        val products = listOf(
            Product(
                100,
                "iPhone 17",
                1299.99,
                "Apple",
                LocalDate.of(2025, 9, 19),
                R.drawable.iphone_17
            ),
            Product(
                101,
                "Galaxy S25",
                1199.99,
                "Samsung",
                LocalDate.of(2025, 2, 7),
                R.drawable.galaxys25
            ),
            Product(
                102,
                "Google Pixel 3",
                1099.99,
                "Google",
                LocalDate.of(2018, 10, 18),
                R.drawable.pixle3
            ),
            Product(
                103,
                "Moto Edge 2024",
                899.99,
                "Motorola",
                LocalDate.of(2024, 5, 2),
                R.drawable.motoedge
            ),
            Product(104, "Asus ROG", 999.99, "Asus", LocalDate.of(2023, 4, 19), R.drawable.asusrog)
        )

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Phone Product List") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(products, key = { it.id }) { product ->
                    ProductCard(product = product)
                }
            }
        }
    }

    @Composable
    fun ProductCard(product: Product) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(product.imageId),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(product.name, style = MaterialTheme.typography.titleLarge)
                    Text(
                        "Manufacturer: ${product.manufacturer}",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        "Launched: ${product.launchDate}",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text("Price: $${product.price}", color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}
