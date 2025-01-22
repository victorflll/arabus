package com.example.arabus.ui

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.arabus.FavoritesScreenPath
import com.example.arabus.HistoryScreenPath
import com.example.arabus.HomeScreenPath
import com.example.arabus.LoginRouteScreen
import com.example.arabus.RegisterRouteScreen
import com.example.arabus.SearchRouteScreenPath
import com.example.arabus.SplashScreenPath
import com.example.arabus.ViewRouteScreenPath
import com.example.arabus.ui.factories.FavoriteViewModelFactory
import com.example.arabus.ui.screens.HomeScreen
import com.example.arabus.ui.view.FavoriteViewModel
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
        startDestination = SplashScreenPath
    ) {
        composable(SplashScreenPath) {
            val context = LocalContext.current
            SplashScreen(navController = navController, context = context)
        }
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
        composable(FavoritesScreenPath) {
            val viewModelStoreOwner = LocalViewModelStoreOwner.current
            viewModelStoreOwner?.let { owner ->
                val favoriteViewModel: FavoriteViewModel = viewModel(owner)
                FavoritesScreen(navController = navController, viewModel = favoriteViewModel)
            }
        }
        composable(FavoritesScreenPath) {
            val viewModelStoreOwner = LocalViewModelStoreOwner.current
            val application = LocalContext.current.applicationContext as Application
            val routeViewModel: RouteViewModel = viewModel()

            viewModelStoreOwner?.let { owner ->
                val favoriteViewModel: FavoriteViewModel = viewModel(
                    owner,
                    factory = FavoriteViewModelFactory(application, routeViewModel)
                )
                FavoritesScreen(navController = navController, viewModel = favoriteViewModel)
            }
        }
        composable(LoginRouteScreen) { ViewLoginScreen(navController) }
        composable(RegisterRouteScreen) { ViewRegisterScreen(navController) }
    }
}