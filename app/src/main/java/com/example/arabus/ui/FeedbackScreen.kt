package com.example.arabus.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.arabus.ui.theme.AppGreen
import com.example.arabus.ui.theme.TypographyColor
import com.example.arabus.ui.view.FeedbackViewModel
import com.example.arabus.core.request.FeedbackRequest

@Composable
fun FeedbackScreen(navController: NavHostController, viewModel: FeedbackViewModel = viewModel()) {
    var rating by remember { mutableStateOf(0) }
    var feedbackText by remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current
    val isLoading by viewModel.isLoading.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {navController.popBackStack()}) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Voltar")
            }
            Text(text = "Feedback", fontSize = 20.sp, modifier = Modifier.padding(start = 8.dp), color = TypographyColor)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Como você nos avalia?",
            fontSize = 18.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            for (i in 1..5) {
                IconButton(onClick = { rating = i }) {
                    Icon(
                        imageVector = Icons.Outlined.Star,
                        contentDescription = "Estrela $i",
                        tint = TypographyColor,
                        modifier = Modifier.size(40.dp)
                    )
                    if (i <= rating) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = TypographyColor,
                            modifier = Modifier.size(40.dp).absoluteOffset(x = (-40).dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Quer nos recomendar algo?",
            fontSize = 18.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(AppGreen.copy(alpha = 0.2f), shape = RoundedCornerShape(8.dp))
                .padding(12.dp),
            contentAlignment = Alignment.TopStart
        ) {
            if (feedbackText.text.isEmpty()) {
                Text(
                    text = "Digite seu feedback",
                    fontSize = 16.sp,
                    color = Color.Black.copy(alpha = 0.20f)
                )
            }
            BasicTextField(
                value = feedbackText,
                onValueChange = { feedbackText = it },
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val request = FeedbackRequest(
                    comment = feedbackText.text,
                    rating = rating
                )
                viewModel.createFeedback(request) { success ->
                    if (success) {
                        Toast.makeText(context, "Feedback enviado com sucesso!", Toast.LENGTH_SHORT).show()
                        feedbackText = TextFieldValue("")
                        rating = 0
                    } else {
                        Toast.makeText(context, "Erro ao enviar feedback.", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = AppGreen),
            shape = RoundedCornerShape(8.dp),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text(text = "Publicar Feedback", fontSize = 16.sp, color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewFeedbackScreen() {
    val navController = rememberNavController()
    val viewModel: FeedbackViewModel = viewModel()
    FeedbackScreen(navController = navController, viewModel = viewModel)
}
