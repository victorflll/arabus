package com.example.arabus.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.arabus.ViewRouteScreenPath
import com.example.arabus.ui.components.AppButton
import com.example.arabus.ui.components.AppOriginToDestination
import com.example.arabus.ui.components.AppTextField
import com.example.arabus.ui.theme.AppGreen
import com.example.arabus.ui.theme.AppWhite
import com.example.arabus.ui.utils.Permissions
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchRouteScreen(navController: NavHostController) {
    var origin = remember { mutableStateOf("") }
    var destination = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = AppGreen,
                    titleContentColor = AppWhite
                ),
                title = { Text("Buscar rotas") },
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
                }
            )
        },
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .semantics { contentDescription = "Tela de busca de rotas" }
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            AppGreen,
                            shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                        )
                        .padding(horizontal = 12.dp)
                        .padding(bottom = 12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        AppOriginToDestination()
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 4.dp)
                        ) {
                            AppTextField(
                                placeholder = "Seu local",
                                textState = origin.value,
                                onValueChange = { origin.value = it },
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = "Ícone de busca de local"
                                    )
                                }
                            )
                            AppTextField(
                                placeholder = "Destino",
                                textState = destination.value,
                                onValueChange = { destination.value = it },
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = "Ícone de busca de destino"
                                    )
                                }
                            )
                        }
                        Box(
                            modifier = Modifier.clickable {
                                val aux = origin.value
                                origin.value = destination.value
                                destination.value = aux
                            }.semantics { contentDescription = "Botão para inverter origem e destino" }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.SwapVert,
                                contentDescription = "Inverter origem e destino",
                                tint = AppWhite
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    BuildBody()
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        AppButton(
                            title = "Verificar rotas",
                            onClick = { navController.navigate(ViewRouteScreenPath) },
                            modifier = Modifier.semantics { contentDescription = "Botão para verificar rotas disponíveis" }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BuildBody() {
    Permissions.RequestLocationPermission { GoogleMapComposable() }
}

@Composable
fun GoogleMapComposable() {
    val defaultZoom = 13f
    val lat = -9.7525
    val lng = -36.6611

    val mapProperties = remember {
        MapProperties(
            isMyLocationEnabled = true
        )
    }

    val uiSettings = remember {
        MapUiSettings(zoomControlsEnabled = true)
    }

    val city = LatLng(lat, lng)
    val mapCamera = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(city, defaultZoom)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize().semantics { contentDescription = "Mapa interativo mostrando localização atual" },
        properties = mapProperties,
        uiSettings = uiSettings,
        cameraPositionState = mapCamera,
        onMyLocationButtonClick = {
            val movement = CameraUpdateFactory.newLatLngZoom(city, defaultZoom)
            mapCamera.move(movement)
            true
        }
    )
}
