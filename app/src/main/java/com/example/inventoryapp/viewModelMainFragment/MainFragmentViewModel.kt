package com.example.inventoryapp.viewModelMainFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.inventoryapp.data.Product
import com.example.inventoryapp.data.ProductRepository

class MainFragmentViewModel(private val repository: ProductRepository) : ViewModel() {

    fun getAllData(): LiveData<List<Product>> {
        return repository.readAllData
    }

    fun getAllSearchProduct(searchWord: String): LiveData<List<Product>> {
        return repository.getAllSearchProduct(searchWord)
    }
}