package com.example.lab_week_05

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

class GlideLoader(private val context: Context) : ImageLoader {
    override fun loadImage(imageUrl: String, imageView: ImageView) {
        Glide.with(context)
            .load(imageUrl) // Sumber gambar dari URL
            .centerCrop()   // Potong gambar agar pas di view
            .into(imageView)  // Tampilkan ke ImageView target
    }
}