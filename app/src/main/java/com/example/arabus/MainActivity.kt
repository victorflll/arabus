package com.example.arabus
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.arabus.components.AppScaffold
import com.example.arabus.ui.FavoritesScreenPath
import com.example.arabus.ui.HistoryScreenPath
import com.example.arabus.ui.HomeScreenPath
import com.example.arabus.ui.SearchRouteScreenPath
import com.example.arabus.ui.ViewRouteScreenPath
import com.example.arabus.ui.components.AppTextField
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

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
        composable(HistoryScreenPath) { HistoryScreen(navController) }
        composable(FavoritesScreenPath) { FavoritesScreen() }
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {
    val textState = remember { mutableStateOf("") }
    AppScaffold(navController = navController) {
        Box(modifier = Modifier.fillMaxSize()) {
            MapView(textState)
            AppTextField(
                placeholder = "Para onde vamos hoje?",
                textState = textState.value,
                onValueChange = { textState.value = it },
                modifier = Modifier.padding(32.dp),
                trailingIcon = {
                    IconButton(onClick = { navController.navigate(SearchRouteScreenPath) }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar")
                    }
                }
            )
        }
    }
}

@Composable
fun MapView(textState: MutableState<String>) {
    val initialPosition = LatLng(-9.754, -36.659)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initialPosition, 14f)
    }

    var markerPosition by remember { mutableStateOf<LatLng?>(null) }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        onMapClick = { latLng ->
            markerPosition = latLng
            textState.value = "Lat: ${latLng.latitude}, Lng: ${latLng.longitude}"  // Preenche o input com a coordenada
        }
    ) {
        markerPosition?.let {
            Marker(
                state = MarkerState(position = it),
                title = "Ponto Selecionado",
                snippet = "Lat: ${it.latitude}, Lng: ${it.longitude}"
            )
        }
    }
}
