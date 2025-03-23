package com.example.arabus.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
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
    var estimatedTime by remember { mutableStateOf<String?>(null) }
    var distance by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        scope.launch {
            val (routePoints, time, routeDistance) = getRoute(startLocation, endLocation, context)
            polylinePoints = routePoints
            estimatedTime = time
            distance = routeDistance

            if (routePoints.isEmpty()) {
                Toast.makeText(context, "Não foi possível obter a rota", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(zoomControlsEnabled = false)
        ) {
            Marker(state = MarkerState(position = startLocation), title = "Origem")
            Marker(state = MarkerState(position = endLocation), title = "Destino")
            if (polylinePoints.isNotEmpty()) {
                Polyline(points = polylinePoints, color = Color.Blue, width = 10f)
            }
        }

        estimatedTime?.let { time ->
            distance?.let { dist ->
                Row(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(16.dp)
                        .background(Color.White, RoundedCornerShape(12.dp))
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.DirectionsBus,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "$time | $dist",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
