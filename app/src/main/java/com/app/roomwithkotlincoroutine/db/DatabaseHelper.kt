package com.app.roomwithkotlincoroutine.db

interface DatabaseHelper {

    suspend fun getUsers(): List<Product>

    suspend fun insertAll(users: List<Product>)

    suspend fun insert(users: Product)
}