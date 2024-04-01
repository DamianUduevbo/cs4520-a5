package com.cs4520.assignment5.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cs4520.assignment5.Models.ProductModel

@Database(entities = [ProductModel::class], version = 1)
abstract class CacheDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDAO

    companion object {
        @Volatile
        private var dbInstance: CacheDatabase? = null
        fun instance(context: Context): CacheDatabase {
            synchronized(this) {
                var instance = dbInstance

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        CacheDatabase::class.java,
                        "productsDB"
                    ).build()

                    dbInstance = instance
                }
                return instance
            }
        }
    }
}