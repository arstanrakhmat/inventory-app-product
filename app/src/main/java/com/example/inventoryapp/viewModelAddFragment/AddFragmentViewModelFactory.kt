package com.example.inventoryapp.viewModelAddFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.inventoryapp.data.ProductRepository

class AddFragmentViewModelFactory(private val repository: ProductRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddFragmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddFragmentViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}