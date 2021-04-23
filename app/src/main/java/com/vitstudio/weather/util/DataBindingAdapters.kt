package com.vitstudio.weather.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import com.vitstudio.weather.data.entity.Weather

@BindingAdapter("app:loadImage")
fun loadImage(view: ImageView, url: String?) {
    Picasso.get().load(url).into(view)
}

@BindingAdapter("app:hideIfZero")
fun hideIfZero(view: View, name: Weather?) {
    view.visibility = if (name != null) View.VISIBLE else View.GONE
}