package com.example.arabus

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.arabus.ui.components.AppButton
import com.example.arabus.ui.components.AppTextField
import com.example.arabus.ui.theme.AppGreen
import com.example.arabus.ui.utils.LoadAsset

private val LogoWidth = 200.dp
private val LogoHeight = 85.dp
private val TitleFontSize = 26.sp
private val SubtitleFontSize = 16.sp
private val LineHeight = 32.sp
private val SpacingBetweenSections = 40.dp
private val PaddingHorizontal = 16.dp

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
                .padding(PaddingHorizontal),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                LoginForm(
                    username = username.value,
                    onUsernameChange = { username.value = it },
                    password = password.value,
                    onPasswordChange = { password.value = it },
                    onForgotPasswordClick = { navController.navigate("home") },
                    onLoginClick = { navController.navigate("search_route") }
                )
            }

            SignupFooter(
                onSignupClick = { navController.navigate("signup") },
                modifier = Modifier.align(Alignment.Start)
            )
        }
    }
}
@Composable
fun LoginForm(
    username: String,
    onUsernameChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onForgotPasswordClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoadAsset.PngExtension("arabus-logo", width = LogoWidth, height = LogoHeight)

        Spacer(modifier = Modifier.height(SpacingBetweenSections +  32.dp))

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Bem-vindo de volta, ")
                }
                withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                    append("siga com o login para o AraBus")
                }
            },
            fontSize = TitleFontSize,
            color = Color.White,
            style = TextStyle(lineHeight = LineHeight),
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .align(Alignment.Start)
                .padding(start = 14.dp, bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(SpacingBetweenSections))

        AppTextField(
            placeholder = "E-mail cadastrado",
            label = "E-mail",
            textState = username,
            onValueChange = onUsernameChange,
            labelColor = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        )

        AppTextField(
            placeholder = "Insira sua senha",
            textState = password,
            onValueChange = onPasswordChange,
            label = "Senha",
            labelColor = Color.White,
            modifier = Modifier.fillMaxWidth(),
            isPassword = true
        )

        AppButton(
            title = "Esqueceu a senha?",
            onClick = onForgotPasswordClick,
            padding = PaddingValues(all = 0.dp),
            modifier = Modifier
                .align(Alignment.End)
        )

        AppButton(
            title = "Login",
            onClick = onLoginClick,
            fontSize = TitleFontSize,
            modifier = Modifier.fillMaxWidth(),
            padding = PaddingValues(all = 0.dp)
        )
    }
}

@Composable
fun SignupFooter(
    onSignupClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 14.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Não tem uma conta?",
            fontSize = SubtitleFontSize,
            color = Color.White
        )
        TextButton(onClick = onSignupClick) {
            Text(
                text = "Cadastre-se",
                fontSize = SubtitleFontSize,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}