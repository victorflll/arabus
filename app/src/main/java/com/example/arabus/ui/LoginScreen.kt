package com.example.arabus.ui

import android.view.accessibility.AccessibilityManager
import android.content.Context
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.*
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.arabus.application.service.user.AuthService
import com.example.arabus.ui.components.AppButton
import com.example.arabus.ui.components.AppTextField
import com.example.arabus.ui.theme.AppGreen
import com.example.arabus.ui.utils.LoadAsset
import com.example.arabus.ui.view.UserViewModel
import kotlinx.coroutines.launch
import androidx.core.content.getSystemService

private val TitleStyle =
    TextStyle(fontSize = 26.sp, lineHeight = 32.sp, fontWeight = FontWeight.Bold)
private val SubtitleFontSize = 16.sp
private val SpacingBetweenSections = 40.dp
private val HorizontalPadding = 16.dp

fun announceForAccessibility(context: Context, message: String) {
    val accessibilityManager = context.getSystemService<AccessibilityManager>()
    accessibilityManager?.sendAccessibilityEvent(
        android.view.accessibility.AccessibilityEvent.obtain().apply {
            eventType = android.view.accessibility.AccessibilityEvent.TYPE_ANNOUNCEMENT
            className = this@apply::class.java.name
            packageName = context.packageName
            text.add(message)
        }
    )
}

@Composable
fun ViewLoginScreen(navController: NavHostController) {
    val userViewModel: UserViewModel = viewModel()
    val authService = remember { AuthService(userViewModel) }

    val username = remember { mutableStateOf("admin@gmail.com") }
    val password = remember { mutableStateOf("admin123") }
    val isLoading = remember { mutableStateOf(false) }
    val loginError = remember { mutableStateOf<String?>(null) }

    val coroutineScope = rememberCoroutineScope()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = AppGreen
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(HorizontalPadding),
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
                    isLoading = isLoading.value,
                    loginError = loginError.value,
                    onForgotPasswordClick = { navController.navigate("forgot_password") },
                    onLoginClick = {
                        if (username.value.isBlank() || password.value.isBlank()) {
                            loginError.value = "Preencha todos os campos."
                        } else {
                            isLoading.value = true
                            loginError.value = null
                            coroutineScope.launch {
                                val isValid =
                                    authService.validateCredentials(username.value, password.value)
                                if (isValid) {
                                    navController.navigate("home")
                                } else {
                                    isLoading.value = false
                                    loginError.value = "Credenciais inválidas. Tente novamente."
                                }
                            }
                        }
                    }
                )
            }

            SignupFooter(
                onSignupClick = { navController.navigate("register_route") },
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
    isLoading: Boolean,
    loginError: String?,
    onForgotPasswordClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoadAsset.PngExtension("arabus-logo", width = 200.dp, height = 85.dp, description = "logotipo do Arabus")

        Spacer(modifier = Modifier.height(SpacingBetweenSections + 32.dp))

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Bem-vindo de volta, ")
                }
                withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                    append("siga com o login para o AraBus")
                }
            },
            style = TitleStyle,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .align(Alignment.Start)
                .padding(start = 14.dp, bottom = HorizontalPadding)
                .semantics { contentDescription = "Mensagem de boas-vindas ao AraBus: Bem-vindo de volta, siga com o login para o AraBus" }
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
                .semantics { contentDescription = "Campo para inserir o e-mail" }
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

        if (!loginError.isNullOrEmpty()) {
            Text(
                text = loginError,
                color = Color.Red,
                fontSize = SubtitleFontSize,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 8.dp, start = 10.dp)
                    .semantics { contentDescription = "Erro: $loginError" }
            )
        }

        TextButton(
            onClick = onForgotPasswordClick,
            modifier = Modifier.align(Alignment.End)
                .semantics { contentDescription = "Botão para recuperar senha" }
        ) {
            Text(
                text = "Esqueceu a senha?",
                fontSize = SubtitleFontSize,
                color = Color.White
            )
        }

        if (isLoading) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            AppButton(
                title = "Login",
                onClick = onLoginClick,
                fontSize = TitleStyle.fontSize,
                modifier = Modifier.fillMaxWidth().semantics { stateDescription = "Carregando login..." },
                padding = PaddingValues(all = 0.dp)
            )
        }
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
            .padding(start = 14.dp, bottom = 16.dp)
            .semantics { contentDescription = "Seção para usuários sem conta" },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Não tem uma conta?",
            fontSize = SubtitleFontSize,
            color = Color.White,
            modifier = Modifier.semantics {
                contentDescription = "Texto informativo: Não tem uma conta?"
            }
        )
        TextButton(
            onClick = onSignupClick,
            modifier = Modifier.semantics { contentDescription = "Botão para cadastro" }
        ) {
            Text(
                text = "Cadastre-se",
                fontSize = SubtitleFontSize,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}
