package com.vitstudio.weather.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vitstudio.weather.R
import com.vitstudio.weather.data.entity.Weather
import com.vitstudio.weather.util.OnItemClickListener
import com.vitstudio.weather.util.loadImage

class WeatherRecycleAdapter(val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<WeatherRecycleAdapter.ViewHolder>() {
    private var weatherList: ArrayList<Weather> = ArrayList()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val cityName: TextView = view.findViewById(R.id.textView_cityName)
        val description: TextView = view.findViewById(R.id.textView_description)
        val temp: TextView = view.findViewById(R.id.textView_temp)
        val icon: ImageView = view.findViewById(R.id.imageView_icon_description)
        val iconLocation: ImageView = view.findViewById(R.id.icon_location)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onItemClickListener.onItemClick(position)
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_item_weather, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = weatherList.elementAt(position)
        holder.apply {
            cityName.text = item.name
            temp.text = item.temp
            description.text = item.description
            loadImage(icon, item.icon)
            if (position == 0) iconLocation.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int = weatherList.size

    fun setList(list: ArrayList<Weather>) {
        weatherList = list
        notifyDataSetChanged()
    }

    fun getList(): List<Weather> = weatherList

    fun deletePosition(weather: Weather) {
        weatherList.remove(weather)
        notifyDataSetChanged()
    }
}