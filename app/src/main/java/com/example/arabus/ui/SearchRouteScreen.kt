package com.example.arabus.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.arabus.ViewRouteScreenPath
import com.example.arabus.components.AppSearchSelect
import com.example.arabus.components.Street
import com.example.arabus.ui.components.AppButton
import com.example.arabus.ui.components.AppOriginToDestination
import com.example.arabus.ui.theme.AppGreen
import com.example.arabus.ui.theme.AppWhite
import com.example.arabus.ui.utils.Permissions
import com.example.arabus.ui.utils.SharedPreferenceManager
import com.example.arabus.ui.view.RouteViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchRouteScreen(navController: NavHostController){
    val originFromPreviousScreen = navController.previousBackStackEntry
        ?.savedStateHandle
        ?.get<String>("origin") ?: ""

    val routeViewModel: RouteViewModel = viewModel()

    val routes by routeViewModel.routes.collectAsState()

    val context = LocalContext.current
    val sharedPreferenceManager = remember { SharedPreferenceManager(context) }
    val isTalkBackEnabled = sharedPreferenceManager.isTalkBackEnabled()

    val origin = remember { mutableStateOf<Street?>(null) }
    val destination = remember { mutableStateOf<Street?>(null) }

    LaunchedEffect(Unit) {
        routeViewModel.loadRoutes()
    }

    val streetsOrigin = routes
        .map { route ->
            Street(
                name = route.origin.street,
                latitude = route.origin.latitude.toDouble(),
                longitude = route.origin.longitude.toDouble()
            )
        }
        .distinctBy { it.name }

    val streetsDestination = routes
        .filter { it.origin.street == origin.value?.name}
        .map { route ->
            Street(
                name = route.destination.street,
                latitude = route.destination.latitude.toDouble(),
                longitude = route.destination.longitude.toDouble()
            )
        }
        .distinctBy { it.name }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarColors(
                    containerColor = AppGreen,
                    scrolledContainerColor = AppGreen,
                    navigationIconContentColor = AppWhite,
                    titleContentColor = AppWhite,
                    actionIconContentColor = AppGreen
                ),
                title = {
                    Text(
                        "Buscar rotas",
                        modifier = Modifier.semantics {
                            contentDescription = "Tela de busca de rotas"
                        }
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.semantics { contentDescription = "Botão voltar" }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
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
                            AppSearchSelect(
                                items = streetsOrigin,
                                placeholder = "Origem",
                                defaultItem = originFromPreviousScreen,
                                onSelect = { name, lat, lng ->
                                    origin.value = Street(name, lat, lng)
                                }
                            )

                            AppSearchSelect(
                                items = streetsDestination,
                                placeholder = "Destino",
                                onSelect = { name, lat, lng ->
                                    destination.value = Street(name, lat, lng)
                                }
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
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        AppButton(
                            title = "Verificar rotas",
                            onClick = {
                                navController.navigate(ViewRouteScreenPath)
                                if (isTalkBackEnabled) {
                                    Toast.makeText(
                                        context,
                                        "Procurando rotas...",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                            modifier = Modifier.semantics {
                                contentDescription = "Botão para verificar rotas disponíveis"
                            }
                        )
                    }

                }
            }
        }
    }
}

@Composable
private fun BuildBody() {
    //Permissions.RequestInternetPermission()
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
        modifier = Modifier
            .fillMaxSize()
            .semantics { contentDescription = "Mapa interativo para visualizar rotas" },
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

//@Composable
//@Preview
//private fun Preview() {
//    SearchRouteScreen(navController = NavHostController(LocalContext.current))
//}
