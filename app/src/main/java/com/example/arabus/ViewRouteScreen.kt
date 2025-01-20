package com.example.arabus

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.arabus.ui.SearchRouteScreenPath
import com.example.arabus.ui.components.AppButton
import com.example.arabus.ui.components.AppOriginToDestination
import com.example.arabus.ui.theme.AppBlack
import com.example.arabus.ui.theme.AppGreen
import com.example.arabus.ui.theme.AppGreenOpacity
import com.example.arabus.ui.theme.AppGrey
import com.example.arabus.ui.theme.AppWhite
import com.example.arabus.ui.utils.LoadAsset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewRouteScreen(navController: NavHostController) {
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
                title = { Text("Rotas disponíveis") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
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
            LazyColumn {
                items(5) { index ->
                    val logo: String = if (index % 2 == 0) {
                        "metropolitana-logo"
                    } else {
                        "real-logo"
                    }

                    BuildCard(
                        routeName = "Expresso Estelar $index",
                        startTime = "07:00",
                        endTime = "07:30",
                        startLocation = "Avenida Ceci Cunha, Centro",
                        endLocation = "IFAL - Campus Arapiraca",
                        duration = "30min",
                        fareInfo = "Sem tarifa",
                        rating = "4.$index",
                        logo = logo
                    )
                    Spacer(modifier = Modifier.height(16.dp))
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
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                LoadAsset.PngExtension(logo)
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
                        Text(text = "Pontuação: $rating")
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Icon(Icons.Outlined.Favorite, contentDescription = null)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Column {
                    Text(text = startTime)
                    Spacer(modifier = Modifier.height(8.dp))
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
            AppButton("Visualizar", onClick =  {
                println("View button clicked for $routeName!")
            })
        }
    }
}

@Composable
@Preview
private fun Preview() {
    ViewRouteScreen(navController = NavHostController(LocalContext.current))
}