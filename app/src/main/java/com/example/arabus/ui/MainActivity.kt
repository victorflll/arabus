package com.example.arabus.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.arabus.FavoritesScreen
import com.example.arabus.FavoritesScreenPath
import com.example.arabus.HistoryScreenPath
import com.example.arabus.HomeScreenPath
import com.example.arabus.LoginRouteScreen
import com.example.arabus.NotificationScreen
import com.example.arabus.NotificationScreenPath
import com.example.arabus.RegisterRouteScreen
import com.example.arabus.SearchRouteScreenPath
import com.example.arabus.ViewRouteScreenPath
import com.example.arabus.ui.screens.HomeScreen
import com.example.arabus.ui.view.HistoryViewModel
import com.example.arabus.ui.view.RouteViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@Composable
private fun App() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = LoginRouteScreen
    ) {
        composable(HomeScreenPath) { HomeScreen(navController) }
        composable(SearchRouteScreenPath) { SearchRouteScreen(navController) }
        composable(ViewRouteScreenPath) {
            val viewModelStoreOwner = LocalViewModelStoreOwner.current
            viewModelStoreOwner?.let { owner ->
                val routeViewModel: RouteViewModel = viewModel(owner)
                ViewRouteScreen(navController = navController, routeViewModel = routeViewModel)
            }
        }
        composable(HistoryScreenPath) {
            val viewModelStoreOwner = LocalViewModelStoreOwner.current
            viewModelStoreOwner?.let { owner ->
                val routeViewModel: RouteViewModel = viewModel(owner)
                val historyViewModel: HistoryViewModel = viewModel(owner)
                HistoryScreen(
                    navController = navController,
                    routeViewModel = routeViewModel,
                    historyViewModel = historyViewModel
                )
            }
        }

        composable(FavoritesScreenPath) { FavoritesScreen(navController) }
        composable(NotificationScreenPath) { NotificationScreen(navController) }
        composable(LoginRouteScreen) { ViewLoginScreen(navController) }
        composable(RegisterRouteScreen) { ViewRegisterScreen(navController) }
    }
}