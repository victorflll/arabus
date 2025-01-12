package com.example.arabus

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.arabus.ui.theme.AppGreenOpacity
import com.example.arabus.ui.theme.ArabusTheme
import com.example.arabus.ui.theme.TypographyColor
import com.example.arabus.ui.theme.AppLightGrey

@Composable
fun NotificationScreen() {
    ArabusTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp).verticalScroll(rememberScrollState())) {
                Spacer(modifier = Modifier.height(36.dp))

                NotificationHeader()

                Spacer(modifier = Modifier.height(20.dp))

                // EXEMPLOS (TESTE)
                val sections = listOf(
                    "Viagem atual para Deputado Nezinho" to listOf(
                        "Seu ônibus chegou!" to "05:20",
                        "Seu ônibus está chegando..." to "05:15"
                    ),
                    "Centro - Deputado Nezinho às 12:30" to listOf(
                        "Desembarque!" to "12:30",
                        "Seu ônibus chegou!" to "12:15",
                        "Seu ônibus está chegando..." to "12:10"
                    ),
                    "Terminal - IFAL às 18:00" to listOf(
                        "Ônibus atrasado" to "18:05",
                        "Ônibus saiu do ponto inicial" to "17:45",
                        "Chegue ao ponto de embarque" to "17:30"
                    ),
                )

                sections.forEach { (title, notifications) ->
                    NotificationSection(title = title, notifications = notifications)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun NotificationHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_notification),
            contentDescription = "Ícone de notificações",
            modifier = Modifier
                .padding(start = 26.dp)
                .width(24.dp)
                .height(32.dp),
            tint = TypographyColor
        )

        Spacer(modifier = Modifier.width(12.dp))

        androidx.compose.material3.Text(
            text = "Notificações",
            style = MaterialTheme.typography.titleLarge.copy(color = TypographyColor)
        )
    }
}

@Composable
fun NotificationSection(title: String, notifications: List<Pair<String, String>>) {
    Column {
        androidx.compose.material3.Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(color = TypographyColor, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(start = 14.dp, bottom = 6.dp)
        )

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = AppLightGrey
        )

        Spacer(modifier = Modifier.height(18.dp))

        notifications.forEach { (message, time) ->
            NotificationCard(message = message, time = time)
            Spacer(modifier = Modifier.height(14.dp))
        }
    }
}

@Composable
fun NotificationCard(message: String, time: String) {
    Card(
        modifier = Modifier
            .padding(horizontal = 14.dp)
            .wrapContentWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(8.dp),
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = AppGreenOpacity
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_notification),
                contentDescription = "Ícone da notificação",
                modifier = Modifier.size(32.dp),
                tint = TypographyColor
            )

            Spacer(modifier = Modifier.width(12.dp))

            androidx.compose.material3.Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge.copy(color = TypographyColor, fontWeight = FontWeight.Bold),
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            androidx.compose.material3.Text(
                text = time,
                style = MaterialTheme.typography.bodySmall.copy(color = TypographyColor, fontWeight = FontWeight.Bold)
            )
        }
    }
}
