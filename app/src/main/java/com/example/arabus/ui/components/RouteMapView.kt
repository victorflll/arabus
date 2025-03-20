package com.example.arabus.ui.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

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
        Log.d("Routemapview", "startLocation: $startLocation")
        Log.d("Routemapview", "endLocation: $endLocation")
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


