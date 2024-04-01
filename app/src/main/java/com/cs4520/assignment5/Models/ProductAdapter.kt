package com.cs4520.assignment5.Models

class ProductAdapter {
    fun toProductModel(product: Product): ProductModel {
        if (product.type == "Food") {
            return ProductModel(
                name = product.name!!,
                expiryDate = product.expiryDate,
                price = product.price!!,
                type = "Food"
            )
        } else if (product.type == "Equipment") {
            return ProductModel(
                name = product.name!!,
                expiryDate = null,
                price = product.price!!,
                type = "Equipment"
            )
        } else {
            throw IllegalArgumentException("Invalid product type")
        }
    }

    fun toProduct(productModel: ProductModel): Product {
        if (productModel.type == "Food") {
            return Product(
                name = productModel.name,
                expiryDate = productModel.expiryDate,
                price = productModel.price,
                type = "Food"
            )
        } else if (productModel.type == "Equipment") {
            return Product(
                name = productModel.name,
                expiryDate = null,
                price = productModel.price,
                type = "Equipment"
            )
        } else {
            throw IllegalArgumentException("Invalid product type")
        }
    }

    fun removeDuplicateProducts(products: List<Product>) {
        val uniqueProducts = mutableListOf<Product>()
        for (product in products) {
            if (!uniqueProducts.contains(product)) {
                uniqueProducts.add(product)
            }
        }
    }
}