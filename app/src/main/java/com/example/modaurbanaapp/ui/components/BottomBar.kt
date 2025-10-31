package com.example.modaurbanaapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.modaurbanaapp.navigation.Screen

@Composable
fun BottomBar(navController: NavHostController) {
    val items: List<Screen> = listOf(Screen.Home, Screen.Catalog)

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDest = navBackStackEntry?.destination

        items.forEach { screen: Screen ->
            val selected = currentDest?.hierarchy?.any { it.route == screen.route } == true

            // Pares de íconos
            val (iconFilled, iconOutlined) = when (screen) {
                Screen.Home -> Icons.Filled.Home to Icons.Outlined.Home
                Screen.Catalog -> Icons.Filled.Storefront to Icons.Outlined.Storefront
            }

            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (!selected) {
                        navController.navigate(screen.route) {
                            // ⚠️ Evita saveState si tu versión no lo expone
                            popUpTo(Screen.Home.route) { inclusive = false }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = { Icon(if (selected) iconFilled else iconOutlined, contentDescription = null) },
                label = { Text(if (screen == Screen.Home) "Inicio" else "Catálogo") }
            )
        }
    }
}
