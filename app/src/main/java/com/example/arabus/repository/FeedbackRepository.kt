package com.example.arabus.repository

import com.example.arabus.core.domain.feedback.FeedbackDomain
import com.example.arabus.core.interfaces.IFeedbackRepository
import com.example.arabus.core.network.RetrofitInstance
import com.example.arabus.core.network.UserManager
import com.example.arabus.core.request.FeedbackRequest


class FeedbackRepository : IFeedbackRepository {
    private val api = RetrofitInstance.feedback

    override suspend fun getFeedbacks(): List<FeedbackDomain> {
        val response = api.getFeedbacks()
        return response.body()?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun createFeedback(feedbackRequest: FeedbackRequest): FeedbackDomain {
        val userId = UserManager.getUserIdAsString()
        val response = api.createFeedback(userId = userId, feedback = feedbackRequest)
        return response.body()?.let { it.toDomain() }
            ?: throw IllegalStateException("Erro ao criar feedback")
    }
}