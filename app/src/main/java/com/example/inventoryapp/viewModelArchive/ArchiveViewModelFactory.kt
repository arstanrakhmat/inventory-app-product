package com.example.inventoryapp.viewModelArchive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.inventoryapp.data.ProductRepository

class ArchiveViewModelFactory(private val repository: ProductRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArchiveViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ArchiveViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}