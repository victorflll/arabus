package com.example.arabus.ui.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.arabus.repository.database.DatabaseInstance
import com.example.arabus.repository.internal.entities.Feedback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FeedbackViewModel(application: Application) : AndroidViewModel(application) {
    private val database = DatabaseInstance.getDatabase(application)
    private val feedbackDao = database.feedbackDao()

    fun insertFeedback(userId: Int, plateLicense: String, comment: String?, rating: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val feedback = Feedback(
                userId = userId,
                plateLicense = plateLicense,
                comment = comment,
                rating = rating
            )
            feedbackDao.insert(feedback)
        }
    }

    suspend fun getFeedbacksByUserId(userId: Int): List<Feedback> {
        return withContext(Dispatchers.IO) {
            feedbackDao.getByUserId(userId)
        }
    }


    suspend fun getFeedbacksByPlateLicense(plateLicense: String): List<Feedback> {
        return withContext(Dispatchers.IO) {
            feedbackDao.getByDriverId(plateLicense)
        }
    }

}