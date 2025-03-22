package com.example.arabus.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

@Composable
fun RouteMapView(
    startLocation: LatLng,
    endLocation: LatLng
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(startLocation, 14f)
    }
    var polylinePoints by remember { mutableStateOf<List<LatLng>>(emptyList()) }

    LaunchedEffect(Unit) {
        scope.launch {
            polylinePoints = getRoute(startLocation, endLocation, context)
            if (polylinePoints.isEmpty()) {
                Toast.makeText(context, "Não foi possível obter a rota", Toast.LENGTH_SHORT).show()
            }
        }
    }

    GoogleMap(modifier = Modifier.fillMaxSize(), cameraPositionState = cameraPositionState) {
        Marker(state = MarkerState(position = startLocation), title = "Origem")
        Marker(state = MarkerState(position = endLocation), title = "Destino")
        if (polylinePoints.isNotEmpty()) {
            Polyline(points = polylinePoints, color = Color.Blue, width = 10f)
        }
    }
}


