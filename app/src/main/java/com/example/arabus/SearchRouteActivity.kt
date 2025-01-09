package com.example.arabus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.arabus.ui.components.AppButton
import com.example.arabus.ui.components.AppOriginToDestination
import com.example.arabus.ui.components.AppTextField
import com.example.arabus.ui.theme.AppGreen
import com.example.arabus.ui.theme.AppWhite
import com.example.arabus.ui.theme.ArabusTheme

class SearchRouteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_3A)
@Composable
private fun AppPreview() {
    App()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun App() {
    var origin by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }

    ArabusTheme {
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
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            println("Back button clicked")
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
            Surface(modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
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
                                    textState = origin,
                                    onValueChange = { newText -> origin = newText },
                                    trailingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.Search,
                                            contentDescription = "Search Icon for origin"
                                        )
                                    }
                                )
                                AppTextField(
                                    placeholder = "Destino",
                                    textState = destination,
                                    onValueChange = { newText -> destination = newText },
                                    trailingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.Search,
                                            contentDescription = "Search Icon for destination"
                                        )
                                    }
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .clickable { println("Swap button clicked") }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.SwapVert,
                                    contentDescription = "Swap Icon",
                                    tint = AppWhite
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))
                    AppButton(
                        title = "Verificar rotas",
                        onClick = { println("Searching routes...") }
                    )
                }
            }

        }
    }
}