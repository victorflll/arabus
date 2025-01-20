package com.example.arabus
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.arabus.ui.HomeScreenPath
import com.example.arabus.ui.SearchRouteScreenPath
import com.example.arabus.ui.ViewRouteScreenPath
import com.example.arabus.ui.components.AppButton

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
        startDestination = HomeScreenPath
    ) {
        composable(HomeScreenPath) { HomeScreen(navController) }
        composable(SearchRouteScreenPath) { SearchRouteScreen(navController) }
        composable(ViewRouteScreenPath) { ViewRouteScreen(navController) }
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            AppButton("Go to Search routes", onClick = {
                navController.navigate(SearchRouteScreenPath)
            })
        }
    }
}