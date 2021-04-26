package com.moneyconversion.model

import android.graphics.drawable.Drawable

data class Money(
    var id: String,
    val image: Drawable?,
    val selected: Boolean
) {
    override fun toString() = id
}