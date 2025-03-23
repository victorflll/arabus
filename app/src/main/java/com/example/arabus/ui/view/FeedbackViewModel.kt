package com.example.arabus.ui.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.arabus.repository.FeedbackRepository
import com.example.arabus.core.domain.feedback.FeedbackDomain
import com.example.arabus.core.network.UserManager
import com.example.arabus.core.request.FeedbackRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FeedbackViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = FeedbackRepository()
    private val _feedbackList = MutableStateFlow<List<FeedbackDomain>>(emptyList())
    val feedbackList: StateFlow<List<FeedbackDomain>> = _feedbackList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun createFeedback(feedbackRequest: FeedbackRequest, onResult: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                val response = repository.createFeedback(feedbackRequest)
                onResult(true)
            } catch (e: Exception) {
                onResult(false)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getFeedbacks() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                _feedbackList.value = repository.getFeedbacks()
            } catch (e: Exception) {
                _feedbackList.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}