package com.example.modaurbanaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.modaurbanaapp.navigation.AppNavGraph
import com.example.modaurbanaapp.ui.theme.ModaUrbanaAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ModaUrbanaAppTheme(darkTheme = true) { // look & feel streetwear
                ModaUrbanaApp()
            }
        }
    }
}

@Composable
fun ModaUrbanaApp() {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MODA URBANA") },
                actions = {
                    IconButton(onClick = { /* TODO: search */ }) { Icon(Icons.Default.Search, null) }
                    IconButton(onClick = { /* TODO: account */ }) { Icon(Icons.Outlined.Person, null) }
                    IconButton(onClick = { /* TODO: cart */ }) { Icon(Icons.Outlined.ShoppingCart, null) }
                }
            )
        }
    ) { inner ->
        AppNavGraph(navController = navController)
    }
}
