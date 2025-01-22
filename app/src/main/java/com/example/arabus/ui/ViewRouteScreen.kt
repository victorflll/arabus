package com.example.arabus.ui

import android.app.Application
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.arabus.ui.components.AppButton
import com.example.arabus.ui.components.AppOriginToDestination
import com.example.arabus.ui.theme.AppBlack
import com.example.arabus.ui.theme.AppGreen
import com.example.arabus.ui.theme.AppGreenOpacity
import com.example.arabus.ui.theme.AppWhite
import com.example.arabus.ui.utils.LoadAsset
import com.example.arabus.ui.utils.timeDifference
import com.example.arabus.ui.utils.toFormattedTime
import com.example.arabus.ui.view.RouteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewRouteScreen(navController: NavHostController, routeViewModel: RouteViewModel) {
    val routes by routeViewModel.routes.collectAsState()
    val isLoading by routeViewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        routeViewModel.loadRoutes()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = AppGreen,
                    navigationIconContentColor = AppWhite,
                    titleContentColor = AppWhite,
                ),
                title = { Text("Rotas disponíveis") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                },
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (routes.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Nenhuma rota disponível.")
                }
            } else {
                LazyColumn(modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
                    items(routes.size) { i ->
                        val route = routes[i].route
                        val address = routes[i]
                        BuildCard(
                            routeName = "Rota ${route.routeCode}",
                            startTime = route.startedAt.toFormattedTime(),
                            endTime = route.finishedAt.toFormattedTime(),
                            startLocation = address.startStreet,
                            endLocation = address.endStreet,
                            duration = route.finishedAt.timeDifference(route.startedAt),
                            fareInfo = route.cost?.takeIf { it > 0 }?.let { "R$ %.2f".format(it) } ?: "Sem tarifa",
                            rating = "4.5",
                            logo = route.pictureUri ?: "arabus-logo",
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}


@Composable
private fun BuildCard(
    routeName: String,
    startTime: String,
    endTime: String,
    startLocation: String,
    endLocation: String,
    duration: String,
    fareInfo: String,
    rating: String,
    logo: String
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = AppGreenOpacity,
            contentColor = AppBlack,
        ),
        border = BorderStroke(1.dp, AppBlack)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                LoadAsset.PngExtension(logo)
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = routeName)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row {
                        Icon(
                            Icons.Outlined.Star,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Pontuação: $rating")
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Icon(Icons.Outlined.Favorite, contentDescription = null)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(text = startTime)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = endTime)
                }
                AppOriginToDestination(
                    height = 12.dp,
                    color = AppBlack,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Column {
                    Text(text = startLocation)
                    Text(text = duration, fontSize = TextUnit(8f, TextUnitType.Sp))
                    Text(text = endLocation)
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(text = fareInfo)
            }
            Spacer(modifier = Modifier.height(4.dp))
            AppButton("Visualizar", onClick = {
                println("View button clicked for $routeName!")
            })
        }
    }
}

@Composable
@Preview
private fun Preview() {
    val navController = NavHostController(LocalContext.current)
    val mockViewModel = RouteViewModel(Application())
    ViewRouteScreen(navController, mockViewModel)
}