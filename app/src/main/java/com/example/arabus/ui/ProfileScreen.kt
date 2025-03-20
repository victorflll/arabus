package com.example.arabus.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.arabus.components.AppScaffold
import com.example.arabus.core.domain.user.User
import com.example.arabus.core.network.UserManager
import com.example.arabus.ui.theme.AppGreenOpacity
import com.example.arabus.ui.theme.AppLightGrey
import com.example.arabus.ui.theme.ArabusTheme
import com.example.arabus.ui.theme.TypographyColor
import com.example.arabus.ui.utils.LoadAsset
import com.example.arabus.ui.components.BaseDialog
import com.example.arabus.ui.components.DialogType


@Composable
fun LogoutDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    BaseDialog(
        title = "Deseja mesmo fazer logout?",
        onDismiss = onDismiss,
        onConfirm = onConfirm,
        buttonText = "Sair",
        type = DialogType.Destructive
    ) {
        Text(
            text = "Esta ação é definitiva e você precisará fazer login novamente para acessar o aplicativo.",
            fontSize = 14.sp,
            color = Color.Red,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ProfileScreen(navController: NavHostController) {
    val user = UserManager.user
    var showLogoutDialog by remember { mutableStateOf(false) }

    ArabusTheme {
        AppScaffold(navController = navController) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(36.dp))
                ProfileHeader()
                Spacer(modifier = Modifier.height(20.dp))
                HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = AppLightGrey)
                Spacer(modifier = Modifier.height(20.dp))
                ProfileButtonsSection(navController, user, onLogoutClick = { showLogoutDialog = true })
            }
        }
    }

    if (showLogoutDialog) {
        LogoutDialog(
            onDismiss = { showLogoutDialog = false },
            onConfirm = {
                showLogoutDialog = false
                navController.navigate("login_route")
            }
        )
    }
}

@Composable
fun ProfileHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Ícone de perfil",
            modifier = Modifier
                .padding(start = 26.dp)
                .size(32.dp), // 🔹 Melhorando tamanho do ícone
            tint = TypographyColor
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = "Perfil",
            style = MaterialTheme.typography.titleLarge.copy(color = TypographyColor)
        )
    }
}

@Composable
fun ProfileButtonsSection(navController: NavHostController, user: User?, onLogoutClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(AppLightGrey),
                contentAlignment = Alignment.Center
            ) {
                LoadAsset.PngExtension("real-logo", width = 64.dp, height = 64.dp)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                horizontalAlignment = Alignment.Start // 🔹 Melhorando alinhamento
            ) {
                Text(
                    text = user?.profile?.name ?: "Nome não disponível",
                    style = MaterialTheme.typography.titleLarge.copy(color = TypographyColor)
                )
                Text(
                    text = user?.profile?.phone ?: "Número não disponível",
                    style = MaterialTheme.typography.bodyMedium.copy(color = TypographyColor)
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(18.dp))

    HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = AppLightGrey)

    Spacer(modifier = Modifier.height(18.dp))

    Column {
        val buttons = listOf(
            Triple("Editar Minhas Informações", Icons.Default.Edit, false),
            Triple("Acessibilidade", Icons.Default.Accessibility, false),
            Triple("Histórico de Corridas", Icons.Default.History, false),
            Triple("Feedback", Icons.Default.Feedback, false),
            Triple("Termos de Uso", Icons.Default.Policy, true),
            Triple("Logout", Icons.AutoMirrored.Filled.Logout, false)
        )

        buttons.forEach { (label, icon, hasDownloadIcon) ->
            val isLogout = label == "Logout"

            ProfileButton(
                label = label,
                icon = icon,
                hasDownloadIcon = hasDownloadIcon,
                isLogout = isLogout,
                onClick = {
                    when (label) {
                        "Histórico de Corridas" -> navController.navigate("history")
                        "Logout" -> onLogoutClick()
                        else -> {/* Outras ações */}
                    }
                }
            )
            Spacer(modifier = Modifier.height(14.dp))
        }
    }
}

@Composable
fun ProfileButton(label: String, icon: ImageVector, hasDownloadIcon: Boolean, isLogout: Boolean = false, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 14.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isLogout) Color.White else AppGreenOpacity,
            contentColor = if (isLogout) Color.Red else TypographyColor
        ),
        border = if (isLogout) BorderStroke(1.dp, Color.Red) else null
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Ícone do botão",
                modifier = Modifier.size(32.dp),
                tint = if (isLogout) Color.Red else TypographyColor
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = if (isLogout) Color.Red else TypographyColor,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.weight(1f)
            )

            if (hasDownloadIcon) {
                Icon(
                    imageVector = Icons.Default.FileDownload,
                    contentDescription = "Ícone de download",
                    modifier = Modifier.size(24.dp),
                    tint = TypographyColor
                )
            }
        }
    }
}