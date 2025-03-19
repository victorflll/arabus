package com.example.arabus.core.domain.history

import com.example.arabus.core.domain.route.Route
import java.time.LocalDateTime
import java.util.UUID

data class History(
    val id: UUID,
    val userId: UUID,
    val finishedAt: LocalDateTime?,
    val route: Route
)
