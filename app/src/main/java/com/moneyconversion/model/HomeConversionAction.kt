package com.moneyconversion.model

sealed class HomeConversionAction {
    object HideKeyboard : HomeConversionAction()
    data class Error(val message: String) : HomeConversionAction()
}