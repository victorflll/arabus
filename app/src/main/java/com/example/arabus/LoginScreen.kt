package com.example.arabus

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.arabus.ui.components.AppButton
import com.example.arabus.ui.components.AppTextField
import com.example.arabus.ui.theme.AppGreen
import com.example.arabus.ui.utils.LoadAsset

@Composable
fun ViewLoginScreen(navController: NavHostController) {
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = AppGreen
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoadAsset.PngExtension("arabus-logo", width = 214.dp, height = 85.dp)

            Spacer(modifier = Modifier.height(64.dp))

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Bem-vindo de volta, ")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                        append("siga com o login para o AraBus")
                    }
                },
                fontSize = 26.sp,
                color = Color.White,
                style = TextStyle(
                    lineHeight = TextUnit(
                        32f, TextUnitType.Sp
                    )
                ),
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .align(Alignment.Start)
                    .padding(start = 14.dp, bottom = 16.dp)

            )

            Spacer(modifier = Modifier.height(48.dp))


            AppTextField(
                placeholder = "E-mail cadastrado",
                label = "E-mail",
                textState = username.value,
                onValueChange = { username.value = it },
                labelColor = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            AppTextField(
                placeholder = "Insira sua senha",
                textState = password.value,
                onValueChange = { password.value = it },
                label = "Senha",
                labelColor = Color.White,
                modifier = Modifier
                    .fillMaxWidth(),
                isPassword = true
            )

            AppButton(
                title = "Esqueceu a senha?",
                onClick = {
                    navController.navigate("home")
                },
                modifier = Modifier
                    .padding(end = 0.dp)
                    .align(Alignment.End)
            )

            AppButton(
                title = "Login",
                onClick = {
                    navController.navigate("home")
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
