package com.example.inventoryapp.data

import androidx.lifecycle.LiveData

class ProductRepository(private val productDao: ProductDao) {

    fun readAllData(isArchived: Boolean) : LiveData<List<Product>> {
        return productDao.readAllData(isArchived)
    }

    fun addProduct(product: Product) {
        productDao.addProduct(product)
    }

    fun updateProduct(product: Product) {
        productDao.updateProduct(product)
    }

    fun getAllSearchProduct(searchWord: String) : LiveData<List<Product>> {
        return productDao.getSearchProduct(searchWord)
    }

    fun getAllSearchArchiveProduct(searchWord: String) : LiveData<List<Product>> {
        return productDao.getSearchProduct(searchWord)
    }

    suspend fun deleteProduct(id: Int) {
        productDao.deleteItem(id)
    }

}