package com.example.inventoryapp.viewModelAdd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.inventoryapp.data.ProductRepository

class AddViewModelFactory(private val repository: ProductRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}