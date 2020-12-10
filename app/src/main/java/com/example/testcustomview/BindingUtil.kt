package com.example.testcustomview

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imgUrl")
fun ImageView.loadImage(imgResourceId: Int) {
    Glide.with(context).load(imgResourceId).into(this)
}