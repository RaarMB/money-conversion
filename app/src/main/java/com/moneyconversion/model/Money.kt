package com.moneyconversion.model

data class Money(
    var id: String,
    val image: Int,
    val selected: Boolean
) {
    override fun toString() = id
}