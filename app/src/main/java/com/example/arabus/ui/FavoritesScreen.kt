package com.example.arabus.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.arabus.repository.internal.models.FavoriteWithRoute
import com.example.arabus.ui.components.AppOriginToDestination
import com.example.arabus.ui.theme.*
import com.example.arabus.ui.utils.LoadAsset
import com.example.arabus.ui.view.FavoriteViewModel

@Composable
fun FavoritesScreen(viewModel: FavoriteViewModel = viewModel()) {
    val userMockId = 1
    var favoriteRoutes by remember { mutableStateOf<List<FavoriteWithRoute>>(emptyList()) }

    LaunchedEffect(Unit) {
        viewModel.getFavoritesWithRoutes(userMockId) { favorites ->
            favoriteRoutes = favorites
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(36.dp))
            FavoritesHeader()
            Spacer(modifier = Modifier.height(20.dp))

            if (favoriteRoutes.isEmpty()) {
                EmptyFavoritesMessage()
            } else {
                LazyColumn {
                    items(favoriteRoutes) { route ->
                        FavoriteRouteCard(route)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun FavoritesHeader() {
    Row(verticalAlignment = Alignment.CenterVertically) {
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
            style = MaterialTheme.typography.titleLarge.copy(color = TypographyColor)
        )
    }
}

@Composable
fun FavoriteRouteCard(route: FavoriteWithRoute) {
    Card(
        colors = CardDefaults.cardColors(containerColor = AppGreenOpacity),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        border = BorderStroke(1.dp, AppBlack)
    ) {
        Column(modifier = Modifier.padding(16.dp, 12.dp, 16.dp, 8.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(text = route.startedAt.toString())
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = route.finishedAt.toString())
                }
                AppOriginToDestination(
                    height = 18.dp,
                    color = AppBlack,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = route.description ?: "Origem desconhecida")
                    Text(
                        text = "${route.finishedAt.time - route.startedAt.time} min",
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Text(text = "Destino desconhecido")
                }
                Text(
                    text = route.cost?.let { "R$ %.2f".format(it) } ?: "Sem tarifa",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                LoadAsset.PngExtension(route.pictureUri ?: "default-logo", width = 84.dp, height = 84.dp)
                Spacer(modifier = Modifier.width(8.dp))
                Column(horizontalAlignment = Alignment.Start) {
                    Text(text = "Rota ${route.routeCode}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Icon(
                            imageVector = Icons.Outlined.Star,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "4.9")
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyFavoritesMessage() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Nenhum favorito salvo",
            style = MaterialTheme.typography.bodyLarge.copy(color = TypographyColor)
        )
    }
}
