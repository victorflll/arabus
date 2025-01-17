import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import com.example.arabus.application.service.user.AuthService
import com.example.arabus.ui.theme.AppGreen
import com.example.arabus.ui.view.UserViewModel
import com.example.arabus.ui.components.AppTextField
import com.example.arabus.ui.components.AppButton

private val TitleStyle = TextStyle(fontSize = 26.sp, lineHeight = 32.sp, fontWeight = FontWeight.Bold)
private val SpacingBetweenSections = 40.dp
private val DefaultPadding = 16.dp

@Composable
fun ViewRegisterScreen(navController: NavHostController) {
    val userViewModel: UserViewModel = viewModel()
    val authService = remember { AuthService(userViewModel) }

    val fullName = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }

    val isLoading = remember { mutableStateOf(false) }
    val loginError = remember { mutableStateOf<String?>(null) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = AppGreen
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(DefaultPadding),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) {
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
                        .padding(start = 14.dp, bottom = DefaultPadding)
                )

                Spacer(modifier = Modifier.height(SpacingBetweenSections))

                AppTextField(
                    placeholder = "Nome completo",
                    label = "Nome completo",
                    textState = fullName.value,
                    onValueChange = { fullName.value = it },
                    labelColor = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                )

                AppTextField(
                    placeholder = "Telefone",
                    label = "Telefone",
                    textState = phone.value,
                    onValueChange = { phone.value = it },
                    labelColor = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                )

                AppTextField(
                    placeholder = "E-mail",
                    label = "E-mail",
                    textState = email.value,
                    onValueChange = { email.value = it },
                    labelColor = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                )

                AppTextField(
                    placeholder = "Senha",
                    label = "Senha",
                    textState = password.value,
                    onValueChange = { password.value = it },
                    labelColor = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                )

                AppTextField(
                    placeholder = "Confirmar senha",
                    label = "Confirmar senha",
                    textState = confirmPassword.value,
                    onValueChange = { confirmPassword.value = it },
                    labelColor = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                )
                AppButton(
                    title = if (isLoading.value) "Aguarde..." else "Cadastrar",
                    fontSize = 24.sp,
                    modifier = Modifier.fillMaxWidth(),
                    padding = PaddingValues(all = 0.dp),
                    onClick = {}
                )
            }
        }
    }
}
