package com.example.modaurbanaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.clickable
import androidx.compose.ui.Alignment
import com.example.modaurbanaapp.model.Category
import com.example.modaurbanaapp.model.Product
import com.example.modaurbanaapp.repository.ProductRepository

@Composable
fun CatalogScreen() {
    var selectedCategory by remember { mutableStateOf(Category.Hoodies) }
    var order by remember { mutableStateOf(Order.Featured) }

    val products = remember(selectedCategory, order) {
        val base = ProductRepository.byCategory(selectedCategory)
        when (order) {
            Order.Featured -> base
            Order.PriceAsc -> base.sortedBy { it.price }
            Order.PriceDesc -> base.sortedByDescending { it.price }
        }
    }

    Column(Modifier.fillMaxSize()) {
        CategoryTabs(
            categories = ProductRepository.allCategories(),
            selected = selectedCategory,
            onSelect = { selectedCategory = it }
        )
        FilterBar(order = order, onOrderChange = { order = it })

        ProductGrid(products = products)
    }
}

private enum class Order { Featured, PriceAsc, PriceDesc }

@Composable
private fun CategoryTabs(
    categories: List<Category>,
    selected: Category,
    onSelect: (Category) -> Unit
) {
    ScrollableTabRow(selectedTabIndex = categories.indexOf(selected)) {
        categories.forEachIndexed { index, cat ->
            Tab(
                selected = cat == selected,
                onClick = { onSelect(cat) },
                text = { Text(cat.name) }
            )
        }
    }
}

@Composable
private fun FilterBar(order: Order, onOrderChange: (Order) -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            "Productos",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )

        // Solo los chips (sin ExposedDropdownMenuBox)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            AssistChip(
                onClick = { onOrderChange(Order.Featured) },
                label = { Text("Destacados") },
                enabled = order != Order.Featured
            )
            AssistChip(
                onClick = { onOrderChange(Order.PriceAsc) },
                label = { Text("Precio ↑") },
                enabled = order != Order.PriceAsc
            )
            AssistChip(
                onClick = { onOrderChange(Order.PriceDesc) },
                label = { Text("Precio ↓") },
                enabled = order != Order.PriceDesc
            )
        }
    }
}

@Composable
private fun ProductGrid(products: List<Product>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(products) { p -> ProductCard(p) }
    }
}

@Composable
private fun ProductCard(p: Product) {
    Card(Modifier.fillMaxWidth().clickable { /* TODO: nav to detail */ }) {
        Column {
            AsyncImage(
                model = p.imageUrl,
                contentDescription = p.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().height(180.dp)
            )
            Column(Modifier.padding(12.dp)) {
                Text(p.name, style = MaterialTheme.typography.bodyMedium, maxLines = 2)
                Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("$${p.price}", style = MaterialTheme.typography.titleMedium)
                    p.oldPrice?.let {
                        Spacer(Modifier.width(8.dp))
                        Text("$$it", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}
