package com.example.arabus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.arabus.ui.theme.ArabusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArabusTheme {
                // Carrega a tela de favoritos
                FavoritesScreen()
            }
        }
    }
}
