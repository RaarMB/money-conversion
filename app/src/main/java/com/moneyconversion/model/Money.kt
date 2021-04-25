package com.moneyconversion.model

import androidx.annotation.DrawableRes

data class Money(
    var id: String,
    @DrawableRes val image: Int,
    val selected: Boolean
) {
    override fun toString() = id
}