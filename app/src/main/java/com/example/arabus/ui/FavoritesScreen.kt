package com.example.arabus.ui

import android.content.Context
import android.view.accessibility.AccessibilityManager
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.arabus.components.AppScaffold
import com.example.arabus.ui.components.AppOriginToDestination
import com.example.arabus.ui.theme.AppBlack
import com.example.arabus.ui.theme.AppGreenOpacity
import com.example.arabus.ui.theme.TypographyColor
import com.example.arabus.ui.utils.LoadAsset
import com.example.arabus.ui.utils.timeDifference
import com.example.arabus.ui.utils.toFormattedTime
import com.example.arabus.ui.view.FavoriteViewModel

@Composable
fun FavoritesScreen(
    navController: NavHostController,
    viewModel: FavoriteViewModel,
    isTalkBackEnabled: Boolean
) {
    val context = LocalContext.current
    val favorites by viewModel.favorites.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadFavorites()
    }

    AppScaffold(navController = navController) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column {
                Spacer(modifier = Modifier.height(36.dp))
                FavoritesHeader()
                Spacer(modifier = Modifier.height(20.dp))

                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.semantics {
                                contentDescription = "Carregando favoritos..."
                            }
                        )
                    }
                } else if (favorites.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Nenhuma rota favoritada.",
                            modifier = Modifier.semantics {
                                contentDescription = "Nenhuma rota favoritada disponível."
                            }
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.padding(
                            top = 16.dp,
                            start = 16.dp,
                            end = 16.dp
                        )
                    ) {
                        items(favorites.size) { i ->
                            val route = favorites[i].route
                            FavoriteRouteCard(
                                route = FavoriteRoute(
                                    startTime = route.route.startedAt.toFormattedTime(),
                                    endTime = route.route.finishedAt.toFormattedTime(),
                                    startLocation = route.startStreet,
                                    endLocation = route.endStreet,
                                    duration = route.route.finishedAt.timeDifference(route.route.startedAt),
                                    price = route.route.cost?.takeIf { it > 0 }
                                        ?.let { "R$ %.2f".format(it) } ?: "Sem tarifa",
                                    logo = route.route.pictureUri ?: "arabus-logo",
                                    line = "Rota ${route.route.routeCode}",
                                    rating = "Nota: 4.5"
                                ),
                                isTalkBackEnabled = isTalkBackEnabled,
                                context = context
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FavoritesHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.semantics { contentDescription = "Tela de Favoritos" }
    ) {
        Icon(
            imageVector = Icons.Outlined.FavoriteBorder,
            contentDescription = "Ícone de favoritos",
            modifier = Modifier
                .padding(start = 26.dp)
                .size(32.dp),
            tint = TypographyColor
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "Favoritos",
            style = MaterialTheme.typography.titleLarge.copy(color = TypographyColor),
            modifier = Modifier.semantics { contentDescription = "Lista de rotas favoritas" }
        )
    }
}

@Composable
fun FavoriteRouteCard(route: FavoriteRoute, isTalkBackEnabled: Boolean, context: Context) {
    Card(
        colors = CardDefaults.cardColors(containerColor = AppGreenOpacity),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .semantics {
                contentDescription =
                    "Rota de ${route.startLocation} para ${route.endLocation}, saída às ${route.startTime}, chegada às ${route.endTime}. Tempo estimado: ${route.duration}. Preço: ${route.price}."
            },
        border = BorderStroke(1.dp, AppBlack)
    ) {
        Column(modifier = Modifier.padding(16.dp, 12.dp, 16.dp, 8.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(text = route.startTime, color = AppBlack)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = route.endTime, color = AppBlack)
                }
                AppOriginToDestination(
                    height = 18.dp,
                    color = AppBlack,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = route.startLocation, color = AppBlack)
                    Text(
                        text = route.duration,
                        fontSize = TextUnit(10f, TextUnitType.Sp),
                        modifier = Modifier.padding(vertical = 4.dp),
                        color = AppBlack
                    )
                    Text(text = route.endLocation, color = AppBlack)
                }
                Text(
                    text = route.price,
                    fontSize = TextUnit(12f, TextUnitType.Sp),
                    modifier = Modifier.padding(start = 8.dp),
                    color = AppBlack
                )
            }
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                LoadAsset.PngExtension(route.logo, width = 84.dp, height = 84.dp)
                Spacer(modifier = Modifier.width(8.dp))
                Column(horizontalAlignment = Alignment.Start) {
                    Text(text = route.line, color = AppBlack)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Icon(
                            imageVector = Icons.Outlined.Star,
                            contentDescription = "Classificação da rota",
                            modifier = Modifier.size(16.dp),
                            tint = AppBlack
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = route.rating, color = AppBlack)
                    }
                }
            }
        }
    }
}

data class FavoriteRoute(
    val startTime: String,
    val endTime: String,
    val startLocation: String,
    val endLocation: String,
    val duration: String,
    val price: String,
    val logo: String,
    val line: String,
    val rating: String
)