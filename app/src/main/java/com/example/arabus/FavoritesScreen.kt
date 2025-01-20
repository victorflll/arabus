package com.example.arabus

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.arabus.ui.components.AppOriginToDestination
import com.example.arabus.ui.theme.*

@Composable
fun FavoritesScreen() {
    ArabusTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(36.dp))
                FavoritesHeader()
                Spacer(modifier = Modifier.height(20.dp))

                val favoriteRoutes = listOf(
                    FavoriteRoute(
                        startTime = "05:20",
                        startLocation = "Cirilo José, Boa Vista",
                        duration = "40min",
                        endTime = "06:00",
                        endLocation = "Terminal Ceci Cunha",
                        price = "R$ 3,50",
                        logoResource = R.drawable.real_arapiraca_logo,
                        line = "221 - Pau D’arco",
                        rating = "4.9"
                    ),
                    FavoriteRoute(
                        startTime = "12:30",
                        startLocation = "Terminal Ceci Cunha",
                        duration = "30min",
                        endTime = "13:00",
                        endLocation = "IFAL - Campus Arapiraca",
                        price = "Sem tarifa",
                        logoResource = R.drawable.metropolitana_logo,
                        line = "003 - Poção",
                        rating = "4.5"
                    )
                )

                favoriteRoutes.forEach { route ->
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
            painter = painterResource(id = R.drawable.ic_heart),
            contentDescription = "Ícone de favoritos",
            modifier = Modifier
                .padding(start = 26.dp)
                .size(28.dp),
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = AppLightGrey),
        border = BorderStroke(2.dp, AppGreen)
    ) {
        Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                TimeColumn(route)
                Spacer(modifier = Modifier.width(8.dp))
                AppOriginToDestination(
                    height = 30.dp,
                    color = TypographyColor,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                AddressAndPriceColumn(route)
            }
            Spacer(modifier = Modifier.height(12.dp))
            LogoAndRatingRow(route)
        }
    }
}

@Composable
fun TimeColumn(route: FavoriteRoute) {
    Column(
        verticalArrangement = Arrangement.spacedBy(30.dp),
        modifier = Modifier.padding(end = 8.dp)
    ) {
        Text(
            text = route.startTime,
            style = MaterialTheme.typography.bodySmall.copy(
                color = TypographyColor,
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = route.endTime,
            style = MaterialTheme.typography.bodySmall.copy(
                color = TypographyColor,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun AddressAndPriceColumn(route: FavoriteRoute) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = route.startLocation,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TypographyColor,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(end = 8.dp)
                    .weight(1f, fill = false),
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = route.price,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TypographyColor,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = route.endLocation,
            style = MaterialTheme.typography.bodySmall.copy(
                color = TypographyColor,
                fontWeight = FontWeight.Bold
            ),
            overflow = TextOverflow.Ellipsis
        )
    }
}


@Composable
fun LogoAndRatingRow(route: FavoriteRoute) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(id = route.logoResource),
            contentDescription = "Logo da empresa",
            modifier = Modifier.size(64.dp),
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = route.line,
                style = MaterialTheme.typography.bodySmall.copy(color = TypographyColor),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Estrela",
                    modifier = Modifier.size(14.dp),
                    tint = TypographyColor
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = route.rating,
                    style = MaterialTheme.typography.bodySmall.copy(color = TypographyColor)
                )
            }
        }
    }
}

data class FavoriteRoute(
    val startTime: String,
    val startLocation: String,
    val duration: String,
    val endTime: String,
    val endLocation: String,
    val price: String,
    val logoResource: Int,
    val line: String,
    val rating: String
)
