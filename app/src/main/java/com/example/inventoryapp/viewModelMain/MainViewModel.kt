package com.example.inventoryapp.viewModelMain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.inventoryapp.data.Product
import com.example.inventoryapp.data.ProductRepository
import java.util.concurrent.Executors

class MainViewModel(private val repository: ProductRepository) : ViewModel() {
    val isDataSaved = MutableLiveData<Boolean?>()
    private val thread = Executors.newSingleThreadExecutor()

    fun getAllData(isArchived: Boolean): LiveData<List<Product>> {
        return repository.readAllData(isArchived)
    }

    fun getAllSearchProduct(searchWord: String): LiveData<List<Product>> {
        return repository.getAllSearchProduct(searchWord)
    }

    fun updateData(product: Product) {
        thread.execute {
            repository.updateProduct(product)
        }

        thread.shutdown()
        isDataSaved.postValue(thread.isShutdown)
    }
}