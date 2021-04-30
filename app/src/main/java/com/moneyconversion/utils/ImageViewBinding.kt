package com.moneyconversion.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("android:src")
fun ImageView.setImage(resourceId: Int) {
    setImageResource(resourceId)
}