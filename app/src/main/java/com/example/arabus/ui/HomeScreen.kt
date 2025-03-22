package com.example.arabus.ui

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.arabus.SearchRouteScreenPath
import com.example.arabus.components.AppScaffold
import com.example.arabus.core.request.FeedbackRequest
import com.example.arabus.ui.components.AppTextField
import com.example.arabus.ui.theme.AppGreen
import com.example.arabus.ui.theme.TypographyColor
import com.example.arabus.ui.utils.Permissions
import com.example.arabus.ui.utils.SharedPreferenceManager
import com.example.arabus.ui.view.FeedbackViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(navController: NavHostController) {
    val context = LocalContext.current
    val sharedPrefManager = remember { SharedPreferenceManager(context) }
    val isTalkBackEnabled = sharedPrefManager.isTalkBackEnabled()

    val textState = remember { mutableStateOf("") }
    val showFeedbackDialog = remember { mutableStateOf(false) }
    val feedbackViewModel: FeedbackViewModel = viewModel()

    AppScaffold(navController = navController) {
        Box(modifier = Modifier.fillMaxSize()) {
            BuildBody(textState, isTalkBackEnabled, showFeedbackDialog)
            AppTextField(
                placeholder = "Para onde vamos hoje?",
                textState = textState.value,
                onValueChange = { textState.value = it },
                modifier = Modifier
                    .padding(32.dp)
                    .semantics { contentDescription = "Campo de busca para digitar o destino desejado" },
                trailingIcon = {
                    Row {
                        IconButton(
                            onClick = { showFeedbackDialog.value = true },
                            modifier = Modifier.semantics {
                                contentDescription = "Botão temporário para testar envio de feedback"
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Feedback, contentDescription = "Testar Feedback")
                        }
                        IconButton(
                            onClick = { navController.navigate(SearchRouteScreenPath) },
                            modifier = Modifier.semantics {
                                contentDescription = "Botão de busca para pesquisar rotas"
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar")
                        }
                    }
                }
            )
        }
    }

    if (showFeedbackDialog.value) {
        FeedbackDialog(
            onDismiss = { showFeedbackDialog.value = false },
            onSubmit = { rating ->
                val request = FeedbackRequest(
                    rating = rating,
                    comment = ""
                )
                feedbackViewModel.createFeedback(request) { success ->
                    Handler(Looper.getMainLooper()).post {
                        showFeedbackDialog.value = false
                        if (success) {
                            Toast.makeText(context, "Feedback enviado com sucesso!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Erro ao enviar feedback.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        )
    }
}

@Composable
private fun BuildBody(textState: MutableState<String>, isTalkBackEnabled: Boolean, showFeedbackDialog: MutableState<Boolean>) {
    Permissions.RequestLocationPermission { MapView(textState, isTalkBackEnabled, showFeedbackDialog) }
}

@Composable
fun MapView(textState: MutableState<String>, isTalkBackEnabled: Boolean, showFeedbackDialog: MutableState<Boolean>) {
    val context = LocalContext.current
    val initialPosition = LatLng(-9.754, -36.659)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initialPosition, 14f)
    }

    var markerPosition by remember { mutableStateOf<LatLng?>(null) }

    GoogleMap(
        modifier = Modifier
            .fillMaxSize()
            .semantics {
                contentDescription = "Mapa interativo, toque para selecionar um ponto no mapa"
            },
        cameraPositionState = cameraPositionState,
        onMapClick = { latLng ->
            markerPosition = latLng
            textState.value = "Lat: ${latLng.latitude}, Lng: ${latLng.longitude}"

            if (isTalkBackEnabled) {
                Toast.makeText(context, "Localização selecionada: ${latLng.latitude}, ${latLng.longitude}", Toast.LENGTH_SHORT).show()
            }
        }
    ) {
        markerPosition?.let {
            Marker(
                state = MarkerState(position = it),
                title = "Ponto Selecionado",
                snippet = "Lat: ${it.latitude}, Lng: ${it.longitude}"
            )
        }
    }
}

@Composable
fun FeedbackDialog(onDismiss: () -> Unit, onSubmit: (Int) -> Unit) {
    var rating by remember { mutableStateOf(0) }
    var isButtonPressed by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = AppGreen,
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Você chegou ao seu destino!",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Gostaria de avaliar sua experiência?",
                    fontSize = 14.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(12.dp))

                Row(horizontalArrangement = Arrangement.Center) {
                    for (i in 1..5) {
                        IconButton(onClick = { rating = i }) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "Estrela $i",
                                tint = if (i <= rating) TypographyColor else Color.White.copy(alpha = 0.3f),
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    isButtonPressed = true
                    onSubmit(rating)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isButtonPressed) AppGreen else Color.White,
                    contentColor = if (isButtonPressed) Color.White else AppGreen
                )
            ) {
                Text(text = "Enviar feedback", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewFeedbackDialog() {
    FeedbackDialog(
        onDismiss = {},
        onSubmit = { rating -> println("Feedback enviado com $rating estrelas!") }
    )
}
