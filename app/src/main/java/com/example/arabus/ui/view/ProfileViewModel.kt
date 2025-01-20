package com.example.arabus.ui.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.arabus.repository.database.DatabaseInstance
import com.example.arabus.repository.internal.entities.Address
import com.example.arabus.repository.internal.entities.Profile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val database = DatabaseInstance.getDatabase(application);
    private val profileDao = database.profileDao();
    private val addressDao = database.addressDao();

    fun insertProfile(name: String, phoneNumber: String, rating: Int, userId: Int){
        viewModelScope.launch(Dispatchers.IO){
            val profile = Profile(
                name = name,
                phoneNumber = phoneNumber,
                rating = rating,
                userId = userId
            )
            profileDao.insert(profile)
        }

    }

    fun getAllProfiles(onResult: (List<Profile>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO){
            val profiles = profileDao.getAll()
            onResult(profiles)
        }

    }

    fun getProfileById(userId: Int, onResult: (Profile?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val profile = profileDao.getById(userId)
            onResult(profile)
        }
    }

    fun updateProfile(userId: Int, name: String?, phoneNumber: String?, address: Address?) {
        viewModelScope.launch(Dispatchers.IO) {
            val existingProfile = profileDao.getById(userId)
            if (existingProfile != null) {
                val updatedUser = existingProfile.copy(
                    name = name ?: existingProfile.name,
                    phoneNumber = phoneNumber ?: existingProfile.phoneNumber
                )

                profileDao.update(updatedUser)
                if(address != null){
                    addressDao.update(address)
                }
            }
        }
    }
}