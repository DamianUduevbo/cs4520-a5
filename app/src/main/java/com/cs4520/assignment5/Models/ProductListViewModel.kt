package com.cs4520.assignment5.Models

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.cs4520.assignment5.ProductDBApp
import com.cs4520.assignment5.AppData.ProductRepository
import com.cs4520.assignment5.AppData.WorkManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductListViewModel(private val repository: ProductRepository, ctx: Context) : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> get() = _products.asStateFlow()
    private val _isLoading = MutableStateFlow<Boolean>(true)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    init {
        fetchProducts((1..10).random())
        WorkManager.scheduleRefresh(ctx)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

                val application = checkNotNull(extras[APPLICATION_KEY])
                return ProductListViewModel(
                    (application as ProductDBApp).appContainer.productRepository,
                    application.applicationContext
                ) as T
            }
        }
    }

    fun fetchProducts(page: Int?) {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response = repository.getProducts(page)
                _products.value = response
            } catch (e: Exception) {
                _products.value = emptyList()

            } finally {
                _isLoading.value = false
            }

        }
    }

}



