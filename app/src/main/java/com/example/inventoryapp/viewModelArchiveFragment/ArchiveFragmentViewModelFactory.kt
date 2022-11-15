package com.example.inventoryapp.viewModelArchiveFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.inventoryapp.data.ProductRepository
import com.example.inventoryapp.viewModelMainFragment.MainFragmentViewModel

class ArchiveFragmentViewModelFactory(private val repository: ProductRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArchiveFragmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ArchiveFragmentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}