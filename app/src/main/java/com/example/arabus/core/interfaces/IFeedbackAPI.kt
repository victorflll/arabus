package com.example.arabus.core.interfaces

import com.example.arabus.core.request.FeedbackRequest
import com.example.arabus.core.response.FeedbackResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface IFeedbackAPI {
    @GET("feedback")
    suspend fun getFeedbacks(): Response<List<FeedbackResponse>>

    @POST("feedback")
    suspend fun createFeedback(
        @Query("user_id") userId: String,
        @Body feedback: FeedbackRequest
    ): Response<FeedbackResponse>
}



