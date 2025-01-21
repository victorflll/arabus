package com.example.arabus.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar {
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("home") },
            icon = { androidx.compose.material3.Icon(Icons.Default.DirectionsBus, contentDescription = "Ônibus") },
            label = { androidx.compose.material3.Text("Ônibus") }
        )
        NavigationBarItem(
            selected = false,
           onClick = {navController.navigate("favorite")},
            icon = { androidx.compose.material3.Icon(Icons.Default.Favorite, contentDescription = "Favoritos") },
            label = { androidx.compose.material3.Text("Favoritos") }
        )
        NavigationBarItem(
            selected = false,
            onClick = {navController.navigate("notifications")},
            icon = { androidx.compose.material3.Icon(Icons.Default.Notifications, contentDescription = "Notificações") },
            label = { androidx.compose.material3.Text("Notificações") }
        )
        NavigationBarItem(
            selected = false,
            onClick = {navController.navigate("history")},
            icon = { androidx.compose.material3.Icon(Icons.Default.Person, contentDescription = "Perfil") },
            label = { androidx.compose.material3.Text("Perfil") }
        )
    }
}
