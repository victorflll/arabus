package com.example.arabus.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.arabus.core.network.UserManager
import com.example.arabus.core.request.FavoriteRequest
import com.example.arabus.ui.components.AppButton
import com.example.arabus.ui.components.RouteDetailsCard
import com.example.arabus.ui.screens.RouteMapView
import com.example.arabus.ui.utils.Permissions
import com.example.arabus.ui.view.FavoriteViewModel
import com.example.arabus.ui.view.RouteViewModel
import com.google.android.gms.maps.model.LatLng
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteScreen(navController: NavHostController, routeViewModel: RouteViewModel, routeId: String?, favoriteViewModel: FavoriteViewModel
) {
    var showDetails by remember { mutableStateOf(false) }

    val userId = UserManager.id

    val routes by routeViewModel.routes.collectAsState()
    val favorites by favoriteViewModel.favorites.collectAsState()

    val selectedRoute = routes.find { it.id.toString() == routeId }
    val isLoading = selectedRoute == null

    val updatingFavorite = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        routeViewModel.loadRoutes()
        userId?.let {
            favoriteViewModel.getFavoritesByUserId(FavoriteRequest(it)) {}
        }
    }

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
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    val isFavorited = favorites.any { it.route.id.toString() == routeId }
                    IconButton(
                        onClick = {
                            if (selectedRoute != null && userId != null && !updatingFavorite.value) {
                                updatingFavorite.value = true
                                val routeUUID = UUID.fromString(routeId!!)
                                if (isFavorited) {
                                    favoriteViewModel.unfavoriteRoute(userId, routeUUID) {
                                        updatingFavorite.value = false
                                    }
                                } else {
                                    favoriteViewModel.favoriteRoute(userId, routeUUID) {
                                        updatingFavorite.value = false
                                    }
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (isFavorited) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = if (isFavorited) "Desfavoritar" else "Favoritar"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                BuildRouteBody(startPosition, endPosition, navController)
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                if (!showDetails) {
                    AppButton(
                        "Mais informações",
                        onClick = { showDetails = true },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(bottom = 16.dp)
                    )
                } else {
                    if (selectedRoute != null) {
                        RouteDetailsCard(selectedRoute) {
                            showDetails = false
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BuildRouteBody(startLocation: LatLng, endLocation: LatLng,  navController: NavHostController) {
    Permissions.RequestLocationPermission { RouteMapView(startLocation, endLocation, navController) }
}
