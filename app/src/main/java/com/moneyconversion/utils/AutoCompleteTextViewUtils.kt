package com.moneyconversion.utils

import android.R
import android.widget.AdapterView
import android.widget.AutoCompleteTextView

fun <T> AutoCompleteTextView.configureNotFilterAdapter(values: List<T>, onItemClick: (T) -> Unit) {
    val adapter = AutocompleteAdapterNotFilter(
        context,
        R.layout.select_dialog_item,
        values
    )

    setAdapter(adapter)
    onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
        onItemClick(values[position])
    }
}