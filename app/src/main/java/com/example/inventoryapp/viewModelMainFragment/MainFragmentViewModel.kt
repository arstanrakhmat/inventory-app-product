package com.example.inventoryapp.viewModelMainFragment

import androidx.lifecycle.LiveData
import com.example.inventoryapp.data.Product
import com.example.inventoryapp.data.ProductRepository

class MainFragmentViewModel(private val repository: ProductRepository) {

    fun getAllData() : LiveData<List<Product>> {
        return repository.readAllData
    }
}