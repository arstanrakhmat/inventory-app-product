package com.example.inventoryapp

import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImage(image: Bitmap) {
    Glide.with(this.context)
        .load(image)
        .into(this)
}