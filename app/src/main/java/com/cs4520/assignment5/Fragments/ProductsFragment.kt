package com.cs4520.assignment5.Fragments

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cs4520.assignment5.Models.ProductListViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun ProductListScreen() {
    val viewModel: ProductListViewModel = viewModel(
        factory = ProductListViewModel.Factory
    )
    val products by viewModel.products.collectAsState()
    val loading by viewModel.isLoading.collectAsState()

    // if we are loading then show a progress bar
    if (loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        if (products.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No Products", fontSize = 20.sp)
            }
        } else {
            LazyColumn {
                items(products) { product ->
                    ProductRow(product)
                }
            }
        }
    }
}