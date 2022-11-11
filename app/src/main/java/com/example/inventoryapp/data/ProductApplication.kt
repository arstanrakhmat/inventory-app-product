package com.example.inventoryapp.data

import android.app.Application

class ProductApplication : Application() {
    private val database by lazy { ProductDatabase.getDatabase(this) }
    val repository by lazy { ProductRepository(database.productDao()) }
}