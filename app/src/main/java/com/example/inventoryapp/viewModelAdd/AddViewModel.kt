package com.example.inventoryapp.viewModelAdd

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.inventoryapp.data.Product
import com.example.inventoryapp.data.ProductRepository
import java.util.concurrent.Executors

class AddViewModel(private val repository: ProductRepository) : ViewModel() {
    val isDataSaved = MutableLiveData<Boolean?>()
    private val thread = Executors.newSingleThreadExecutor()

    fun addData(product: Product) {
        thread.execute {
            repository.addProduct(product)
        }
        thread.shutdown()
        isDataSaved.postValue(thread.isShutdown)
    }

    fun updateData(product: Product) {
        thread.execute {
            repository.updateProduct(product)
        }

        thread.shutdown()
        isDataSaved.postValue(thread.isShutdown)
    }
}

