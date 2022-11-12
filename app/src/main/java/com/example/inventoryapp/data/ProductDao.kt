package com.example.inventoryapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addProduct(product: Product)

    @Query("SELECT * FROM product_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Product>>

    @Update(entity = Product::class)
    fun updateProduct(product: Product)
}