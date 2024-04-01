package com.cs4520.assignment5.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "products", indices = [Index(value = ["name", "price"], unique = true)])
data class ProductModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    @ColumnInfo(name = "expiry_date")
    val expiryDate: String?,
    val price: Double,
    val type: String
)

