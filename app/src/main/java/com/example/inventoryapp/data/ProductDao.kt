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

    @Query("SELECT * FROM product_table WHERE isArchived = :isArchived ORDER BY id ASC")
    fun readAllData(isArchived: Boolean): LiveData<List<Product>>

    @Update(entity = Product::class)
    fun updateProduct(product: Product)

    @Query("SELECT * FROM product_table WHERE nameProduct LIKE :searchWord ORDER BY nameProduct ASC")
    fun getSearchProduct(searchWord: String): LiveData<List<Product>>

    @Query("SELECT * FROM product_table WHERE nameProduct LIKE :searchWord AND isArchived = :isArchived ORDER BY nameProduct ASC")
    fun getSearchArchiveProduct(
        searchWord: String,
        isArchived: Boolean = true
    ): LiveData<List<Product>>

    @Query("DELETE FROM product_table WHERE id = :id")
    suspend fun deleteItem(id: Int)
}