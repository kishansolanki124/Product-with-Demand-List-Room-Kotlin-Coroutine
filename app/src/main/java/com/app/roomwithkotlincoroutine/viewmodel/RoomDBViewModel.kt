package com.app.roomwithkotlincoroutine.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.roomwithkotlincoroutine.db.DatabaseHelper
import com.app.roomwithkotlincoroutine.db.Product
import kotlinx.coroutines.launch

class RoomDBViewModel(private val dbHelper: DatabaseHelper) : ViewModel() {

    private val users = MutableLiveData<List<Product>>()

    init {
        fetchUsers()
    }

    fun fetchUsers() {
        viewModelScope.launch {
            //users.postValue(Resource.loading(null))
            val usersFromDb = dbHelper.getUsers()
            if (usersFromDb.isEmpty()) {
                users.postValue(usersFromDb)
            } else {
                users.postValue(usersFromDb)
            }
        }
    }

    fun addUser(product: Product) {
        viewModelScope.launch {
            dbHelper.insert(product)
            //users.postValue(Resource.success(usersFromDb))
        }
    }

    fun getUsers(): LiveData<List<Product>> {
        return users
    }
}