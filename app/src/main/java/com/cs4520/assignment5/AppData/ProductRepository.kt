package com.cs4520.assignment5.AppData

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.cs4520.assignment5.API.ApiService
import com.cs4520.assignment5.database.CacheDatabase
import com.cs4520.assignment5.Models.Product
import com.cs4520.assignment5.Models.ProductAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository(
    private val apiService: ApiService,
    private val db: CacheDatabase,
    private val context: Context
) {
    private var productAdapter = ProductAdapter()

    suspend fun getProducts(page: Int?): List<Product> {
        val productsList = mutableListOf<Product>()
        try {
            if (isNetowrkConnected()) {
                val res = apiService.getProducts(page)

                if (!res.isSuccessful || res.body() == null || res.body()!!.isEmpty()) {
                    return withContext(Dispatchers.IO) {
                        retrieveFromCache()
                    }
                } else if (res.isSuccessful && res.body() != null && res.body()!!.isNotEmpty()) {
                    val resProducts = res.body()!!.map {
                        Product(it.name, it.expiryDate, it.price, it.type)!!
                    }

                    for (product in resProducts) {
                        productsList.add(product)
                        productAdapter.removeDuplicateProducts(productsList)
                    }
                    try {
                        withContext(Dispatchers.IO) {
                            addToCache(productsList)
                        }
                    } catch (e: Exception) {
                        return withContext(Dispatchers.IO) {
                            retrieveFromCache()
                        }
                    }
                    return productsList
                }
            } else {
                return withContext(Dispatchers.IO) {
                    retrieveFromCache()
                }
            }
        } catch (e: Exception) {
            return withContext(Dispatchers.IO) {
                retrieveFromCache()
            }
        }

        return emptyList()
    }

    private fun isNetowrkConnected(): Boolean {
        val connManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val networkInfo = connManager.activeNetwork ?: return false
        val capabilities = connManager.getNetworkCapabilities(networkInfo) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private suspend fun addToCache(products: List<Product>) {

        products.map { productAdapter.toProductModel(it) }.let {
            db.productDao()
                .insertAllProducts(it)
        }
    }

    private suspend fun retrieveFromCache(): List<Product> {
        val productEntities = db.productDao()
            .fetchAllProducts()
        return productEntities.map {
            productAdapter.toProduct(it)
        }
    }

}
