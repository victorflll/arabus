package com.example.arabus.ui

import android.app.Application
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
                title = { Text("Histórico de Corridas") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
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
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn {
                    items(history.size) { index ->
                        val historyItem = history[index]
                        val routeItem = routes.find { it.route.routeCode == historyItem.routeId.toString() }

                        if (routeItem != null) {
                            BuildCard(
                                routeName = "Rota ${routeItem.route.routeCode}",
                                startTime = routeItem.route.startedAt.toFormattedTime(),
                                endTime = routeItem.route.finishedAt.toFormattedTime(),
                                startLocation = routeItem.startStreet,
                                endLocation = routeItem.endStreet,
                                duration = routeItem.route.finishedAt.timeDifference(routeItem.route.startedAt),
                                fareInfo = routeItem.route.cost?.takeIf { it > 0 }?.let { "R$ %.2f".format(it) } ?: "Sem tarifa",
                                rating = "4.$index",
                                logo = routeItem.route.pictureUri ?: "arabus-logo",
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
        modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
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
                    Text(text = startTime)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = endTime)
                }
                AppOriginToDestination(
                    height = 18.dp,
                    color = AppBlack,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Column {
                    Text(text = startLocation)
                    Text(text = duration, fontSize = TextUnit(8f, TextUnitType.Sp), modifier = Modifier.padding(vertical = 6.dp))
                    Text(text = endLocation)
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = fareInfo,
                    modifier = Modifier.align(Alignment.Top)
                )
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
                    Text(text = routeName)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Icon(
                            Icons.Outlined.Star,
                            contentDescription = null,
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