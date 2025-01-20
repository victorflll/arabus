package com.example.arabus.ui.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.arabus.repository.database.DatabaseInstance
import com.example.arabus.repository.internal.entities.Feedback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

    fun getFeedbacksByUserId(userId: Int, onResult: (List<Feedback>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val feedbacks = feedbackDao.getByUserId(userId)
            onResult(feedbacks)
        }
    }

    fun getFeedbacksByPlateLicense(plateLicense: String, onResult: (List<Feedback>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val feedbacks = feedbackDao.getByDriverId(plateLicense)
            onResult(feedbacks)
        }
    }
}