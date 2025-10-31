package com.example.modaurbanaapp.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.modaurbanaapp.ui.theme.ModaUrbanaAppTheme

data class ProductUi(
    val id: String,
    val name: String,
    val price: String,
    val oldPrice: String? = null,
    val imageUrl: String
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val banners = remember {
        listOf(
            // Usa imágenes libres/propias; por ahora demo
            "https://picsum.photos/id/1011/1200/600",
            "https://picsum.photos/id/1015/1200/600",
            "https://picsum.photos/id/1016/1200/600"
        )
    }
    val products = remember {
        listOf(
            ProductUi("1","Polerón Heavyweight® On God – Negro", "$53.990", "$60.000","https://picsum.photos/id/100/600/600"),
            ProductUi("2","Polerón Heavyweight® Maltese Cross – Rojo", "$44.990", "$50.000","https://picsum.photos/id/101/600/600"),
            ProductUi("3","Polerón Heavyweight® Maltese Cross – Negro", "$44.990", "$50.000","https://picsum.photos/id/102/600/600"),
            ProductUi("4","Polerón Heavyweight® Jesus Saves – Washed", "$53.990", "$60.000","https://picsum.photos/id/103/600/600"),
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        // Banner / Hero
        val pagerState = rememberPagerState(pageCount = { banners.size })
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        ) { page ->
            AsyncImage(
                model = banners[page],
                contentDescription = "Banner $page",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(Modifier.height(20.dp))

        // Título sección
        Text(
            "ÚLTIMOS LANZAMIENTOS",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(8.dp))

        // Grilla de productos 2/3 columnas según ancho
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 160.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(products) { p ->
                ProductCard(p)
            }
        }
    }
}

@Composable
private fun ProductCard(p: ProductUi) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(Modifier.fillMaxWidth()) {
            AsyncImage(
                model = p.imageUrl,
                contentDescription = p.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )
            Column(Modifier.padding(12.dp)) {
                Text(p.name, style = MaterialTheme.typography.bodyMedium, maxLines = 2)
                Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(p.price, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                    if (p.oldPrice != null) {
                        Spacer(Modifier.width(8.dp))
                        Text(
                            p.oldPrice,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ModaUrbanaAppTheme(darkTheme = true) {
        HomeScreen()
    }
}
