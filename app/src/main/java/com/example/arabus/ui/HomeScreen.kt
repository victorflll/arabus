package com.example.arabus.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.arabus.SearchRouteScreenPath
import com.example.arabus.components.AppScaffold
import com.example.arabus.ui.components.AppTextField
import com.example.arabus.ui.utils.Permissions
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun HomeScreen(navController: NavHostController) {
    val textState = remember { androidx.compose.runtime.mutableStateOf("") }
    AppScaffold(navController = navController) {
        Box(modifier = Modifier.fillMaxSize()) {
            BuildBody(textState)
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
private fun BuildBody(textState: MutableState<String>) {
    Permissions.RequestLocationPermission { MapView(textState) }
}

@Composable
fun MapView(textState: MutableState<String>) {
    val initialPosition = LatLng(-9.754, -36.659)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initialPosition, 14f)
    }

    var markerPosition by remember { androidx.compose.runtime.mutableStateOf<LatLng?>(null) }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        onMapClick = { latLng ->
            markerPosition = latLng
            textState.value =
                "Lat: ${latLng.latitude}, Lng: ${latLng.longitude}"
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
