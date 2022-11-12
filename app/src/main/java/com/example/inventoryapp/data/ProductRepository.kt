package com.example.inventoryapp.data

import androidx.lifecycle.LiveData

class ProductRepository(private val productDao: ProductDao) {

    val readAllData: LiveData<List<Product>> = productDao.readAllData()

    fun addProduct(product: Product) {
        productDao.addProduct(product)
    }

    fun updateProduct(product: Product) {
        productDao.updateProduct(product)
    }
}