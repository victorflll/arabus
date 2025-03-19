package com.example.arabus.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.arabus.core.network.UserManager
import com.example.arabus.core.request.LoginRequest
import com.example.arabus.ui.components.AppButton
import com.example.arabus.ui.components.AppTextField
import com.example.arabus.ui.theme.AppGreen
import com.example.arabus.ui.utils.LoadAsset
import com.example.arabus.ui.utils.SharedPreferenceManager
import com.example.arabus.ui.view.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val TitleStyle =
    TextStyle(fontSize = 26.sp, lineHeight = 32.sp, fontWeight = FontWeight.Bold)
private val SubtitleFontSize = 16.sp
private val SpacingBetweenSections = 40.dp
private val HorizontalPadding = 16.dp

@Composable
fun ViewLoginScreen(navController: NavHostController) {
    val context = LocalContext.current
    val sharedPrefManager = remember { SharedPreferenceManager(context) }
    val isTalkBackEnabled = sharedPrefManager.isTalkBackEnabled()

    val userViewModel: UserViewModel = viewModel()

    val username = remember { mutableStateOf("joao@example.com") }
    val password = remember { mutableStateOf("1234") }
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
                                try {
                                    userViewModel.login(
                                        LoginRequest(
                                            username.value,
                                            password.value
                                        )
                                    ) { token ->
                                        if (token != null) {
                                            coroutineScope.launch(Dispatchers.Main) {
                                                userViewModel.getUser {
                                                    UserManager.id = it?.id
                                                    UserManager.name = it?.profile?.name
                                                    UserManager.email = it?.email
                                                    UserManager.profile = it
                                                }
                                                navController.navigate("home")
                                                Toast.makeText(
                                                    context,
                                                    "Login bem-sucedido",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        } else {
                                            coroutineScope.launch(Dispatchers.Main) {
                                                isLoading.value = false
                                                Toast.makeText(
                                                    context,
                                                    "Credenciais inválidas",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    }
                                } catch (e: Exception) {
                                    withContext(Dispatchers.Main) {
                                        isLoading.value = false
                                        Toast.makeText(
                                            context,
                                            "Erro inesperado.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
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
        Box(
            modifier = Modifier.semantics {
                contentDescription = "Logotipo do AraBus"
            }
        ) {
            LoadAsset.PngExtension("arabus-logo", width = 200.dp, height = 85.dp)
        }

        Spacer(modifier = Modifier.height(40.dp))

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
                .semantics { contentDescription = "Mensagem de boas-vindas ao AraBus" }
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
            modifier = Modifier
                .align(Alignment.End)
                .semantics {
                    contentDescription = "Botão para recuperar senha"
                }
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
                CircularProgressIndicator(
                    modifier = Modifier.semantics { contentDescription = "Carregando login..." }
                )
            }
        } else {
            AppButton(
                title = "Login",
                onClick = onLoginClick,
                fontSize = TitleStyle.fontSize,
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { contentDescription = "Botão de login" },
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
            .padding(start = 14.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Não tem uma conta?",
            fontSize = SubtitleFontSize,
            color = Color.White
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
