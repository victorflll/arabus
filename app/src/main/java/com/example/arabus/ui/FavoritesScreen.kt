package com.example.arabus.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.arabus.R
import com.example.arabus.ui.components.AppOriginToDestination
import com.example.arabus.ui.theme.*
import com.example.arabus.ui.utils.LoadAsset

@Composable
fun FavoritesScreen() {
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

            val favoriteRoutes = listOf(
                FavoriteRoute(
                    startTime = "05:20",
                    endTime = "06:00",
                    startLocation = "Cirilo José, Boa Vista",
                    endLocation = "Terminal Ceci Cunha",
                    duration = "40min",
                    price = "R$ 3,50",
                    logo = "real-logo",
                    line = "221 - Pau D’arco",
                    rating = "4.9"
                ),
                FavoriteRoute(
                    startTime = "12:30",
                    endTime = "13:00",
                    startLocation = "Terminal Ceci Cunha",
                    endLocation = "IFAL - Campus Arapiraca",
                    duration = "30min",
                    price = "Sem tarifa",
                    logo = "metropolitana-logo",
                    line = "003 - Poção",
                    rating = "4.5"
                )
            )

            LazyColumn {
                items(favoriteRoutes) { route ->
                    FavoriteRouteCard(route)
                    Spacer(modifier = Modifier.height(16.dp))
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
fun FavoriteRouteCard(route: FavoriteRoute) {
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
                    Text(text = route.startTime)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = route.endTime)
                }
                AppOriginToDestination(
                    height = 18.dp,
                    color = AppBlack,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = route.startLocation)
                    Text(
                        text = route.duration,
                        fontSize = TextUnit(10f, TextUnitType.Sp),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Text(text = route.endLocation)
                }
                Text(
                    text = route.price,
                    fontSize = TextUnit(12f, TextUnitType.Sp),
                    modifier = Modifier.padding(start = 8.dp)
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
                    Text(text = route.line)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Icon(
                            imageVector = Icons.Outlined.Star,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = route.rating)
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

@Composable
@Preview(showBackground = true)
fun FavoritesScreenPreview() {
    FavoritesScreen()
}
