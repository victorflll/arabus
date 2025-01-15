package com.example.arabus.ui.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.arabus.repository.database.DatabaseInstance
import com.example.arabus.repository.internal.entities.Address
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddressViewModel(application: Application) : AndroidViewModel(application) {
    private val database = DatabaseInstance.getDatabase(application)
    private val addressDao = database.addressDao()

    fun insertAddress(userId: Int, neighborhood: String, street: String, postcode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val address = Address(
                userId = userId,
                neighborhood = neighborhood,
                street = street,
                postcode = postcode
            )
            addressDao.insert(address)
        }
    }

    fun getAddressByUserId(userId: Int, onResult: (Address?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val address = addressDao.getByUserId(userId)
            onResult(address)
        }
    }

    fun updateAddress(userId: Int, neighborhood: String?, street: String?, postcode: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val existingAddress = addressDao.getByUserId(userId)
            if (existingAddress != null) {
                val updatedAddress = existingAddress.copy(
                    neighborhood = neighborhood ?: existingAddress.neighborhood,
                    street = street ?: existingAddress.street,
                    postcode = postcode ?: existingAddress.postcode
                )
                addressDao.update(updatedAddress)
            }
        }
    }
}
