package com.example.arabus.ui

import android.app.Application
import android.content.Context
import android.view.accessibility.AccessibilityManager
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.arabus.components.AppScaffold
import com.example.arabus.repository.internal.entities.History
import com.example.arabus.ui.components.AppOriginToDestination
import com.example.arabus.ui.theme.AppBlack
import com.example.arabus.ui.theme.AppGreen
import com.example.arabus.ui.theme.AppGreenOpacity
import com.example.arabus.ui.theme.AppGrey
import com.example.arabus.ui.theme.AppWhite
import com.example.arabus.ui.utils.LoadAsset
import com.example.arabus.ui.utils.timeDifference
import com.example.arabus.ui.utils.toFormattedTime
import com.example.arabus.ui.view.HistoryViewModel
import com.example.arabus.ui.view.RouteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(navController: NavHostController, routeViewModel: RouteViewModel, historyViewModel: HistoryViewModel) {
    val userMockId = 1
    val routes by routeViewModel.routes.collectAsState()
    val isLoading by routeViewModel.isLoading.collectAsState()

    var history by rememberSaveable { mutableStateOf<List<History>>(emptyList()) }

    LaunchedEffect(Unit) {
        historyViewModel.getHistoryByUserId(userMockId) { historyList ->
            history = historyList
        }
        routeViewModel.loadRoutes()
    }

    AppScaffold(
        navController = navController,
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
                        "Histórico de Corridas",
                        modifier = Modifier.semantics { contentDescription = "Histórico de corridas anteriores" }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar para a tela anterior"
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
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.semantics { contentDescription = "Carregando histórico de corridas..." }
                    )
                }
            } else {
                LazyColumn {
                    items(history.size) { index ->
                        val historyItem = history[index]
                        val routeItem = routes.find { it.route.id == historyItem.routeId }

                        if (routeItem != null) {
                            BuildCard(
                                routeName = "Rota ${routeItem.route.routeCode}",
                                startTime = routeItem.route.startedAt.toFormattedTime(),
                                endTime = routeItem.route.finishedAt.toFormattedTime(),
                                startLocation = routeItem.startStreet,
                                endLocation = routeItem.endStreet,
                                duration = routeItem.route.finishedAt.timeDifference(routeItem.route.startedAt),
                                fareInfo = routeItem.route.cost?.takeIf { it > 0 }?.let { "R$ %.2f".format(it) } ?: "Sem tarifa",
                                rating = "Nota: 4.$index",
                                logo = routeItem.route.pictureUri ?: "arabus-logo"
                            )
                        }
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
        colors = CardColors(
            containerColor = AppGreenOpacity,
            contentColor = AppBlack,
            disabledContainerColor = AppGrey,
            disabledContentColor = AppBlack,
        ),
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .semantics {
                contentDescription = "Histórico de ${routeName}, saída às ${startTime}, chegada às ${endTime}, origem em ${startLocation} e destino ${endLocation}. Duração estimada ${duration}, preço ${fareInfo}, nota ${rating}."
            },
        border = BorderStroke(1.dp, AppBlack)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Column {
                    Text(text = startTime, modifier = Modifier.semantics { contentDescription = "Horário de saída: $startTime" })
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = endTime, modifier = Modifier.semantics { contentDescription = "Horário de chegada: $endTime" })
                }
                AppOriginToDestination(
                    height = 18.dp,
                    color = AppBlack,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Column {
                    Text(text = startLocation, modifier = Modifier.semantics { contentDescription = "Origem: $startLocation" })
                    Text(
                        text = duration,
                        fontSize = TextUnit(8f, TextUnitType.Sp),
                        modifier = Modifier.padding(vertical = 6.dp).semantics { contentDescription = "Duração: $duration" }
                    )
                    Text(text = endLocation, modifier = Modifier.semantics { contentDescription = "Destino: $endLocation" })
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(text = fareInfo, modifier = Modifier.semantics { contentDescription = "Preço: $fareInfo" })
            }
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                LoadAsset.PngExtension(logo, width = 84.dp, height = 84.dp)
                Spacer(modifier = Modifier.width(8.dp))
                Column(horizontalAlignment = Alignment.Start) {
                    Text(text = routeName, modifier = Modifier.semantics { contentDescription = "Nome da rota: $routeName" })
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Icon(
                            Icons.Outlined.Star,
                            contentDescription = "Classificação: $rating",
                            modifier = Modifier.height(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = rating)
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun Preview() {
    val navController = NavHostController(LocalContext.current)
    val mockRouteViewModel = RouteViewModel(Application())
    val mockHistoryViewModel = HistoryViewModel(Application())
    HistoryScreen(navController, mockRouteViewModel, mockHistoryViewModel)
}