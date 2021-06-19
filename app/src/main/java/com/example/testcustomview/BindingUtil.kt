package com.example.testcustomview

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imgUrl")
fun ImageView.loadImage(imgResourceId: Int?) {
    Glide.with(context).load(imgResourceId).into(this)
}


@BindingAdapter(value = ["attribute_name1", "attribute_name2", "attribute_name3"], requireAll = false)
fun method(view: View?, attribute_name1: String?, attribute_name2: Int?, attribute_name3: Boolean?) {

}

@BindingAdapter(value = ["attribute_name1", "attribute_name2", "attribute_name3"], requireAll = false)
fun View.method2(attribute_name1: String?, attribute_name2: Int?, attribute_name3: Boolean?) {

}