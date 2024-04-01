package com.cs4520.assignment5.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cs4520.assignment5.Models.ProductModel

@Dao
interface ProductDAO {
    @Query("SELECT * FROM products")
    suspend fun fetchAllProducts(): List<ProductModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProducts(products: List<ProductModel>)
}