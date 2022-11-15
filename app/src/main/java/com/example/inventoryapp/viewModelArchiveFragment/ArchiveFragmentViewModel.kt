package com.example.inventoryapp.viewModelArchiveFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventoryapp.data.Product
import com.example.inventoryapp.data.ProductRepository
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

class ArchiveFragmentViewModel(private val repository: ProductRepository) : ViewModel() {

    val archiveProducts = repository.readAllData(true)

    fun getSearchArchiveProduct(searchProduct: String): LiveData<List<Product>> {
        return repository.getAllSearchArchiveProduct(searchProduct)
    }

    fun deleteProduct(id: Int) {
        viewModelScope.launch {
            repository.deleteProduct(id)
        }
    }

    fun unArchiveData(product: Product) {
        val thread = Executors.newSingleThreadExecutor()
        thread.execute {
            repository.updateProduct(
                Product(
                    product.id,
                    product.image,
                    product.nameProduct,
                    product.price,
                    product.ownerProduct,
                    product.amountPr,
                    false,
                    )
            )
        }
        thread.shutdown()
    }
}