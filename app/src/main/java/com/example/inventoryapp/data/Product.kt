package com.example.inventoryapp.data

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val image: Bitmap,
    val nameProduct: String,
    val price: Double,
    val ownerProduct: String,
    val amountPr: Int
): java.io.Serializable