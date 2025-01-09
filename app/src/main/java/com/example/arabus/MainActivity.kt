package com.example.arabus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.arabus.repository.database.DatabaseInstance
import com.example.arabus.repository.internal.entities.User
import com.example.arabus.ui.theme.ArabusTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArabusTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    UserGreeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun UserGreeting(modifier: Modifier = Modifier) {
    // Contexto para inicializar o banco de dados
    val context = LocalContext.current
    val database = remember { DatabaseInstance.getDatabase(context) }
    val userDao = database.userDao()

    // Estado para gerenciar o usuário recuperado
    var user by remember { mutableStateOf<User?>(null) }
    val scope = rememberCoroutineScope()

    // Efeito colateral para carregar dados na inicialização
    LaunchedEffect(Unit) {
        scope.launch(Dispatchers.IO) {
            try {
                // Inserir um usuário de teste, caso a tabela esteja vazia
                if (userDao.getAll().isEmpty()) {
                    userDao.insert(User(email = "test@example.com", password = "test_token"))
                }

                // Recuperar o primeiro usuário do banco de dados
                user = userDao.getById(0)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Exibir os dados carregados ou um texto padrão
    Text(
        text = user?.let {
            "Hello ${it.email}! Your token is: ${it.password}"
        } ?: "Loading user data...",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun UserGreetingPreview() {
    ArabusTheme {
        UserGreeting()
    }
}
