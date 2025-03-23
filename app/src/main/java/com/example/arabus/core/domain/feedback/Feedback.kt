package com.example.arabus.core.domain.feedback

import java.util.UUID

data class FeedbackDomain(
    val id: UUID,
    val userId: UUID,
    val driverId: UUID,
    val comment: String?,
    val rating: Int
)
