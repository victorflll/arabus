package com.example.arabus.ui.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.arabus.repository.database.DatabaseInstance
import com.example.arabus.repository.internal.entities.Accessibility
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccessibilityViewModel(application: Application) : AndroidViewModel(application) {
    private val database = DatabaseInstance.getDatabase(application)
    private val accessibilityDao = database.accessibilityDao()


    suspend fun getAccessibilityByUserId(userId: Int): Accessibility? {
        return withContext(Dispatchers.IO) {
            accessibilityDao.getByUserId(userId)
        }
    }

    fun updateAccessibility(
        userId: Int,
        colorblind: Boolean? = null,
        oneHanded: Boolean? = null,
        contrast: Boolean? = null,
        screenReader: Boolean? = null
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val existingSettings = accessibilityDao.getByUserId(userId)
            if (existingSettings != null) {
                val updatedSettings = existingSettings.copy(
                    colorblind = colorblind ?: existingSettings.colorblind,
                    oneHanded = oneHanded ?: existingSettings.oneHanded,
                    contrast = contrast ?: existingSettings.contrast,
                    screenReader = screenReader ?: existingSettings.screenReader
                )
                accessibilityDao.update(updatedSettings)
            }
        }
    }
}
