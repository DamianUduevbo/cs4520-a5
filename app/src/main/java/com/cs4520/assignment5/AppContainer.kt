package com.cs4520.assignment5

import android.content.Context
import androidx.room.Room
import com.cs4520.assignment5.API.Api
import com.cs4520.assignment5.database.CacheDatabase
import com.cs4520.assignment5.AppData.ProductRepository

class AppContainer {
    private lateinit var cacheDatabase: CacheDatabase
    lateinit var productRepository: ProductRepository
    private val apiDatabase = Api.apiService
    private var instance: Boolean = false

    fun createCacheDatabase(context: Context) {
        cacheDatabase = Room.databaseBuilder(
            context, CacheDatabase::class.java, "productsDB"
        ).build()
    }

    fun createRepository(context: Context) {
        if (!instance && this::cacheDatabase.isInitialized) {
            productRepository = ProductRepository(apiDatabase, cacheDatabase, context)
            instance = true
        }
    }
}