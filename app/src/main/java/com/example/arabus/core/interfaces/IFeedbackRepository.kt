package com.example.arabus.core.interfaces

import com.example.arabus.core.domain.feedback.FeedbackDomain
import com.example.arabus.core.request.FeedbackRequest

interface IFeedbackRepository {
    suspend fun getFeedbacks(): List<FeedbackDomain>

    suspend fun createFeedback(feedbackRequest: FeedbackRequest): FeedbackDomain
}