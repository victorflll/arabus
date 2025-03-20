package com.example.arabus.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.arabus.ui.components.AppButton
import com.example.arabus.ui.utils.Permissions
import com.example.arabus.ui.view.RouteViewModel
import com.google.android.gms.maps.model.LatLng

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteScreen(navController: NavHostController, routeViewModel: RouteViewModel, routeId: String?) {
    LaunchedEffect(Unit) {
        routeViewModel.loadRoutes()
    }

    val routes by routeViewModel.routes.collectAsState()
    val selectedRoute = routes.find { it.id.toString() == routeId }
    val isLoading = selectedRoute == null
    val startLatitude = selectedRoute?.origin?.latitude?.toDoubleOrNull() ?: 0.0
    val startLongitude = selectedRoute?.origin?.longitude?.toDoubleOrNull() ?: 0.0
    val endLatitude = selectedRoute?.destination?.latitude?.toDoubleOrNull() ?: 0.0
    val endLongitude = selectedRoute?.destination?.longitude?.toDoubleOrNull() ?: 0.0

    val startPosition = LatLng(startLatitude, startLongitude)
    val endPosition = LatLng(endLatitude, endLongitude)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Rota em tempo real") },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.semantics { contentDescription = "Botão voltar" }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { println("Favoritar") },
                        modifier = Modifier.semantics { contentDescription = "Favoritar" }
                    ) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Favoritar"
                        )
                    }
                }
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                BuildRouteBody(startPosition, endPosition)
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 84.dp)
                    .align(Alignment.BottomCenter)
            ) {
                AppButton(
                    "Mais informações",
                    onClick = { println("Mais informações") },
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}


@Composable
private fun BuildRouteBody(startLocation: LatLng, endLocation: LatLng) {
    Permissions.RequestLocationPermission { RouteMapView(startLocation, endLocation) }
}
