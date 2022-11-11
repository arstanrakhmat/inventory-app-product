package com.example.inventoryapp.data

import androidx.lifecycle.LiveData

class ProductRepository(private val productDao: ProductDao) {

    val readAllData: LiveData<List<Product>> = productDao.readAllData()

    suspend fun addProduct(product: Product) {
        productDao.addProduct(product)
    }
}