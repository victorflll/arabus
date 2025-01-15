package com.example.arabus.ui.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.arabus.repository.database.DatabaseInstance
import com.example.arabus.repository.internal.entities.Bus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BusViewModel(application: Application) : AndroidViewModel(application) {
    private val database = DatabaseInstance.getDatabase(application)
    private val busDao = database.busDao()

    fun insertBus(licensePlate: String, color: String, userId: Int, company: String, imageUrl: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            val bus = Bus(
                licensePlate = licensePlate,
                color = color,
                userId = userId,
                company = company,
                imageUrl = imageUrl
            )
            busDao.insert(bus)
        }
    }

    suspend fun getBusById(licensePlate: String): Bus? {
        return withContext(Dispatchers.IO) {
            busDao.getById(licensePlate)
        }
    }

    suspend fun getBusByLicensePlate(licensePlate: String): Bus? {
        return withContext(Dispatchers.IO) {
            busDao.getById(licensePlate)
        }
    }

    fun updateBus(licensePlate: String, color: String?, userId: Int?, company: String?, imageUrl: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val existingBus = busDao.getById(licensePlate)
            if (existingBus != null) {
                val updatedBus = existingBus.copy(
                    color = color ?: existingBus.color,
                    userId = userId ?: existingBus.userId,
                    company = company ?: existingBus.company,
                    imageUrl = imageUrl ?: existingBus.imageUrl
                )
                busDao.update(updatedBus)
            }
        }
    }

    suspend fun getAllBuses(): List<Bus> {
        return withContext(Dispatchers.IO) {
            busDao.getAll()
        }
    }

}
