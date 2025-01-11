package com.example.arabus.ui.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.arabus.ui.components.AppButton
import com.google.accompanist.permissions.MultiplePermissionsState

class Permissions {
    companion object {
        @OptIn(ExperimentalPermissionsApi::class)
        @Composable
        fun RequestLocationPermission(onSuccess: @Composable () -> Unit? = {}) {
            val locationPermissionsState = rememberMultiplePermissionsState(
                permissions = listOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )

            when {
                locationPermissionsState.allPermissionsGranted -> onSuccess()
                    ?: BuildPermissionMessage("localização")

                locationPermissionsState.shouldShowRationale -> {
                    BuildPermissionConfirmation("localização", locationPermissionsState)
                }

                else -> {
                    BuildPermissionConfirmation("localização", locationPermissionsState)
                }
            }
        }

        @OptIn(ExperimentalPermissionsApi::class)
        @Composable
        fun RequestInternetPermission(onSuccess: @Composable () -> Unit? = {}) {
            val internetPermissionState = rememberMultiplePermissionsState(
                permissions = listOf(
                    android.Manifest.permission.INTERNET
                )
            )

            when {
                internetPermissionState.allPermissionsGranted -> onSuccess()
                    ?: BuildPermissionMessage("internet")

                internetPermissionState.shouldShowRationale -> {
                    BuildPermissionConfirmation("internet", internetPermissionState)
                }

                else -> {
                    BuildPermissionConfirmation("internet", internetPermissionState)
                }
            }
        }

        @Composable
        private fun BuildPermissionMessage(key: String) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Permissão de $key concedida, mas um erro ocorreu.",
                    modifier = Modifier.padding(bottom = 16.dp),
                    style = TextStyle(
                        fontSize = TextUnit(16f, TextUnitType.Sp)
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }

        @OptIn(ExperimentalPermissionsApi::class)
        @Composable
        private fun BuildPermissionConfirmation(key: String, state: MultiplePermissionsState) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Precisamos da permissão de $key para usar essa funcionalidade.",
                    modifier = Modifier.padding(bottom = 16.dp),
                    style = TextStyle(
                        fontSize = TextUnit(16f, TextUnitType.Sp)
                    ),
                    textAlign = TextAlign.Center
                )
                AppButton(
                    title = "Permitir",
                    onClick = { state.launchMultiplePermissionRequest() }
                )
            }
        }
    }
}
