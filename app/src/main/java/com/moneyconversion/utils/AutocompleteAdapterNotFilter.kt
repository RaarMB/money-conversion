package com.moneyconversion.utils

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter

class AutocompleteAdapterNotFilter<T>(context: Context, layout: Int, var values: List<T>) :
    ArrayAdapter<T>(context, layout, values) {
    private val filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val results = FilterResults()
            results.values = values
            results.count = values.size
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            notifyDataSetChanged()
        }
    }

    override fun getFilter(): Filter {
        return filter
    }
}