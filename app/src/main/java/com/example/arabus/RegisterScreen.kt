package com.example.arabus

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.arabus.application.service.user.RegisterService
import com.example.arabus.ui.theme.AppGreen
import com.example.arabus.ui.view.UserViewModel
import com.example.arabus.ui.components.AppTextField
import com.example.arabus.ui.components.AppButton
import com.example.arabus.ui.view.ProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val TitleStyle = TextStyle(
    fontSize = 26.sp,
    lineHeight = 32.sp,
    fontWeight = FontWeight.Bold
)
private val DefaultPadding = 16.dp
private val SubtitleFontSize = 16.sp

@Composable
fun ViewRegisterScreen(navController: NavHostController) {
    val userViewModel: UserViewModel = viewModel()
    val profileViewModel: ProfileViewModel = viewModel()
    val registerService = remember { RegisterService(userViewModel, profileViewModel) }

    val fullName = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }

    val isLoading = remember { mutableStateOf(false) }
    val generalError = remember { mutableStateOf<String?>(null) }

    fun validateFields(): Boolean {
        return when {
            fullName.value.isBlank() -> {
                generalError.value = "Por favor, preencha o campo Nome completo."
                false
            }
            phone.value.isBlank() -> {
                generalError.value = "Por favor, preencha o campo Telefone."
                false
            }
            email.value.isBlank() -> {
                generalError.value = "Por favor, preencha o campo E-mail."
                false
            }
            password.value.isBlank() || confirmPassword.value.isBlank() -> {
                generalError.value = "Por favor, preencha os campos de senha."
                false
            }
            password.value != confirmPassword.value -> {
                generalError.value = "As senhas não coincidem."
                false
            }
            else -> {
                generalError.value = null
                true
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = AppGreen
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(DefaultPadding),
            verticalArrangement = Arrangement.spacedBy(DefaultPadding)
        ) {
            Header()

            RegistrationForm(
                fullName = fullName,
                phone = phone,
                email = email,
                password = password,
                confirmPassword = confirmPassword,
                generalError = generalError
            )

            AppButton(
                title = if (isLoading.value) "Aguarde..." else "Cadastrar",
                fontSize = 24.sp,
                modifier = Modifier.fillMaxWidth(),
                padding = PaddingValues(0.dp),
                onClick = {
                    if (validateFields()) {
                        isLoading.value = true
                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                registerService.registerUser(
                                    fullName.value,
                                    phone.value,
                                    email.value,
                                    password.value
                                )
                                withContext(Dispatchers.Main) {
                                    isLoading.value = false
                                    navController.navigate("login_route")
                                }
                            } catch (e: Exception) {
                                withContext(Dispatchers.Main) {
                                    isLoading.value = false
                                    generalError.value = "Algo deu errado! Tente novamente."
                                }
                            }
                        }
                    }
                }
            )

            LoginFooter(
                onSignupClick = { navController.navigate("login_route") },
                modifier = Modifier.align(Alignment.Start)
            )
        }
    }
}

@Composable
private fun Header() {
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Seja bem-vindo, ")
            }
            withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                append("crie sua conta aqui e ande com o Arabus")
            }
        },
        style = TitleStyle,
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .padding(start = 14.dp, bottom = 10.dp)
    )
}
@Composable
private fun RegistrationForm(
    fullName: MutableState<String>,
    phone: MutableState<String>,
    email: MutableState<String>,
    password: MutableState<String>,
    confirmPassword: MutableState<String>,
    generalError: MutableState<String?>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        AppTextField(
            placeholder = "Nome completo",
            label = "Nome completo",
            textState = fullName.value,
            onValueChange = { fullName.value = it },
            labelColor = Color.White
        )
        AppTextField(
            placeholder = "Telefone",
            label = "Telefone",
            textState = phone.value,
            onValueChange = { phone.value = it },
            labelColor = Color.White
        )
        AppTextField(
            placeholder = "E-mail",
            label = "E-mail",
            textState = email.value,
            onValueChange = { email.value = it },
            labelColor = Color.White
        )
        AppTextField(
            placeholder = "Senha",
            label = "Senha",
            textState = password.value,
            onValueChange = { password.value = it },
            labelColor = Color.White,
            isPassword = true
        )
        AppTextField(
            placeholder = "Confirmar senha",
            label = "Confirmar senha",
            textState = confirmPassword.value,
            onValueChange = { confirmPassword.value = it },
            labelColor = Color.White,
            isPassword = true
        )
        generalError.value?.let {
            Text(
                text = it,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 6.dp)
            )
        }
    }
}

@Composable
fun LoginFooter(
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
            text = "Já possui uma conta?",
            fontSize = SubtitleFontSize,
            color = Color.White
        )
        TextButton(onClick = onSignupClick) {
            Text(
                text = "Entre aqui",
                fontSize = SubtitleFontSize,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}