package com.example.arabus.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun AppScaffold(
    navController: NavHostController,
    topBar: (@Composable () -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = { topBar?.invoke() },
        bottomBar = { BottomNavigationBar(navController) },
        content = { padding ->
            content(padding)
        }
    )
}
