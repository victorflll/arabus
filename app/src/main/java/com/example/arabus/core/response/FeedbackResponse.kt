package com.example.arabus.core.response

import com.example.arabus.core.domain.feedback.FeedbackDomain
import com.google.gson.annotations.SerializedName
import java.util.UUID

data class FeedbackResponse(
    val id: UUID,
    @SerializedName("user_id")
    val userId: UUID,
    @SerializedName("driver_id")
    val driverId: UUID,
    val comment: String,
    val rating: Int
) {
    fun toDomain(): FeedbackDomain {
        return FeedbackDomain(
            id = id,
            userId = userId,
            driverId = driverId,
            comment = comment,
            rating = rating
        )
    }
}
