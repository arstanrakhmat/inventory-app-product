package com.example.inventoryapp.viewModelAddFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.inventoryapp.data.Product
import com.example.inventoryapp.data.ProductRepository
import java.util.concurrent.Executors

class AddFragmentViewModel(private val repository: ProductRepository) : ViewModel() {
    val isDataSaved = MutableLiveData<Boolean?>()
    private val thread = Executors.newSingleThreadExecutor()

    fun addData(product: Product) {
        thread.execute {
            repository.addProduct(product)
        }
        thread.shutdown()
        isDataSaved.postValue(thread.isShutdown)
    }
}

