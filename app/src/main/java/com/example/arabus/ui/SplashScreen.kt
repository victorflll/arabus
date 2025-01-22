package com.example.arabus.ui

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.arabus.LoginRouteScreen
import com.example.arabus.SplashScreenPath
import com.example.arabus.repository.database.DatabaseInstance
import com.example.arabus.repository.database.DatabaseSeeder
import com.example.arabus.ui.theme.AppGreen
import com.example.arabus.ui.utils.LoadAsset
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


@Composable
fun SplashScreen(navController: NavHostController, context: Context) {
    var isDatabaseReady by remember { mutableStateOf(false) }
    var hasWaitedMinimumTime by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            val db = DatabaseInstance.getDatabase(context)

            db.openHelper.writableDatabase

            DatabaseSeeder.populateDatabase(db)
        }
        isDatabaseReady = true
    }

    LaunchedEffect(Unit) {
        delay(1000)
        hasWaitedMinimumTime = true
    }

    if (isDatabaseReady && hasWaitedMinimumTime) {
        LaunchedEffect(Unit) {
            navController.navigate(LoginRouteScreen) {
                popUpTo(SplashScreenPath) { inclusive = true }
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = AppGreen
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LoadAsset.PngExtension("arabus-logo", width = 200.dp, height = 85.dp)
        }
    }
}

