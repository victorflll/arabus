package com.example.arabus

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.arabus.repository.internal.entities.Notification
import com.example.arabus.ui.theme.AppGreenOpacity
import com.example.arabus.ui.theme.ArabusTheme
import com.example.arabus.ui.theme.TypographyColor
import com.example.arabus.ui.theme.AppLightGrey
import com.example.arabus.ui.utils.toFormattedTime
import com.example.arabus.ui.view.NotificationViewModel

@Composable
fun NotificationScreen(viewModel: NotificationViewModel = viewModel()) {
    val notifications = remember { mutableStateListOf<Notification>() }

    LaunchedEffect(Unit) {
        viewModel.getNotificationsByUserId(1) { fetchedNotifications ->
            notifications.clear()
            notifications.addAll(fetchedNotifications)
        } }

    ArabusTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(36.dp))

                NotificationHeader()

                Spacer(modifier = Modifier.height(20.dp))

                if (notifications.isEmpty()) {
                    EmptyNotificationsMessage()
                } else {
                    val groupedNotifications = notifications
                        .groupBy { it.title }
                        .map { (title, items) ->
                            title to items.map { it.message to it.timestamp.toFormattedTime() }
                        }

                    groupedNotifications.forEach { (title, messages) ->
                        NotificationSection(title = title, notifications = messages)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
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

        Text(
            text = "Notificações",
            style = MaterialTheme.typography.titleLarge.copy(color = TypographyColor)
        )
    }
}

@Composable
fun NotificationSection(title: String, notifications: List<Pair<String, String>>) {
    Column {
        Text(
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
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
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

            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge.copy(color = TypographyColor, fontWeight = FontWeight.Bold),
                modifier = Modifier.weight(1f),
                maxLines = Int.MAX_VALUE,
                overflow = TextOverflow.Visible
            )

            Text(
                text = time,
                style = MaterialTheme.typography.bodySmall.copy(color = TypographyColor, fontWeight = FontWeight.Bold)
            )
        }
    }
}

@Composable
fun EmptyNotificationsMessage() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Nenhuma notificação disponível",
            style = MaterialTheme.typography.bodyLarge.copy(color = TypographyColor)
        )
    }
}
