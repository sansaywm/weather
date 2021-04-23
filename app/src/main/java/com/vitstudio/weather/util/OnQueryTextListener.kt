package com.vitstudio.weather.util

import androidx.appcompat.widget.SearchView

class OnQueryTextListener(val onQueryText: (String) -> Unit) :
    SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String?): Boolean {
        onQueryText(query ?: "")
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }
}