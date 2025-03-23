package com.example.arabus.ui.screens


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.arabus.R
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.delay
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
    var busPosition by remember { mutableStateOf(startLocation) }
    var estimatedTime by remember { mutableStateOf<String?>(null) }
    var distance by remember { mutableStateOf<String?>(null) }
    var remainingTime by remember { mutableStateOf<String?>(null) }
    var remainingDistance by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        scope.launch {
            val (routePoints, time, routeDistance) = getRoute(startLocation, endLocation, context)
            polylinePoints = routePoints
            estimatedTime = time
            distance = routeDistance

            if (routePoints.isEmpty()) {
                Toast.makeText(context, "Não foi possível obter a rota", Toast.LENGTH_SHORT).show()
                return@launch
            }

            var fraction = 0f
            while (fraction < 1f) {
                delay(100)
                fraction += 0.001f
                busPosition = getBusPositionAlongRoute(routePoints, fraction)

                val remainingRoutePoints = routePoints.subList((fraction * routePoints.size).toInt(), routePoints.size)
                remainingDistance = calculateRemainingDistance(busPosition, remainingRoutePoints)
                remainingTime = calculateRemainingTime(remainingDistance!!)

                // Verificar se chegou ao destino
                if (isCloseToDestination(busPosition, endLocation)) {
                    // Mostrar Toast quando o ônibus chegar ao destino
                    Toast.makeText(context, "Ônibus chegou ao destino", Toast.LENGTH_LONG).show()
                    break
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(zoomControlsEnabled = false)
        ) {
            Marker(
                state = MarkerState(position = startLocation),
                title = "Origem",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
            )

            Marker(state = MarkerState(position = endLocation), title = "Destino")
            Marker(
                state = MarkerState(position = busPosition),
                title = "Ônibus",
                icon = getBusImageBitmap(context = context, resourceId = R.drawable.bus)
            )

            if (polylinePoints.isNotEmpty()) {
                Polyline(points = polylinePoints, color = Color.Blue, width = 10f)
            }
        }

        estimatedTime?.let { time ->
            distance?.let { dist ->
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(16.dp)
                        .background(Color.White, RoundedCornerShape(12.dp))
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
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

                    remainingTime?.let { remainingTime ->
                        remainingDistance?.let { remainingDist ->
                            Text(
                                text = "Faltam: $remainingTime | $remainingDist km",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}

fun isCloseToDestination(busPosition: LatLng, destination: LatLng, threshold: Double = 50.0): Boolean {
    return SphericalUtil.computeDistanceBetween(busPosition, destination) < threshold
}

