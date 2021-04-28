package com.vitstudio.weather.ui.search

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.afollestad.materialdialogs.MaterialDialog
import com.vitstudio.weather.R
import com.vitstudio.weather.databinding.FragmentSearchWeatherBinding
import com.vitstudio.weather.ui.weatheradd.WeatherAddFragment
import com.vitstudio.weather.util.*

class SearchWeatherFragment : Fragment(), OnItemClickListener {
    private var _binding: FragmentSearchWeatherBinding? = null
    private val binding: FragmentSearchWeatherBinding
        get() = _binding!!
    private lateinit var viewModel: SearchWeatherViewModel
    lateinit var mToolbar: Toolbar
    lateinit var adapter: WeatherRecycleAdapter
    lateinit var itemTouchHelper: com.vitstudio.weather.util.ItemTouchHelper
    private val fragment = WeatherAddFragment()
    private val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewModel = ViewModelProvider(this).get(SearchWeatherViewModel::class.java)
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_search_weather, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mToolbar = APP_ACTIVITY.mToolbar
        initRecycle()
        viewModel.weatherList.observe(viewLifecycleOwner, {
            adapter.setList(it)
        })
    }

    override fun onStart() {
        super.onStart()
        visibilityToolbar()
        APP_ACTIVITY.buttonBack.disableDrawer()
        viewModel.loadWeathersFromDatabase()
    }

    private fun visibilityToolbar() {
        mToolbar.apply {
            visibility = View.VISIBLE
            title = ""
        }
        binding.mainToolbar.visibility = View.VISIBLE
        setHasOptionsMenu(true)
    }

    private fun initRecycle() {
        val adapterView = binding.weatherAdapter
        adapter = WeatherRecycleAdapter(this)
        adapterView.adapter = adapter
        adapterView.hasFixedSize()
        deleteWeather()
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.weatherAdapter)
    }

    override fun onItemClick(position: Int) {
        val arrayAlarm = adapter.getList()
        val clickedItem = arrayAlarm[position]
        bundle.apply {
            putString(KEY_CITY_NAME, clickedItem.name)
            putBoolean(KEY_ADD_NEW, false)
            fragment.arguments = this
            val nav = findNavController()
            nav.navigate(R.id.weatherAddFragment, this)
        }
    }

    private fun deleteWeather() {
        itemTouchHelper = ItemTouchHelper { viewHolder ->
            MaterialDialog(requireContext())
                .show {
                    title(R.string.delete_city)
                    message(R.string.delete_city_message)
                    negativeButton(R.string.close) {
                        adapter.notifyDataSetChanged()
                    }
                    positiveButton(R.string.delete) {
                        val position = viewHolder.adapterPosition
                        val arrayAlarm = adapter.getList()
                        val clickedItem = arrayAlarm[position]
                        adapter.deletePosition(clickedItem)
                        viewModel.deleteWhetherDatabase(clickedItem.name!!)
                    }
                }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val search = menu.findItem(R.id.action_menu_search)
        val searchView = search.actionView as SearchView

        searchView.apply {
            queryHint = "Введите местоположение"
            setOnQueryTextListener(OnQueryTextListener {
                viewModel.loadWeatherCityName(it)
                initSearchCity()
            })

            setOnCloseListener {
                binding.cardSearchCity.visibility = View.GONE
                return@setOnCloseListener false
            }
        }
        return
    }

    private fun initSearchCity() {
        viewModel.weather.observe(viewLifecycleOwner, { weather ->
            if (weather != null) {
                binding.apply {
                    textViewSearchCity.text = weather.name
                    textViewTemp.text = weather.temp
                    cardSearchCity.visibility = View.VISIBLE

                    cardSearchCity.setOnClickListener {
                        bundle.putString(KEY_CITY_NAME, weather.name)
                        bundle.putBoolean(KEY_ADD_NEW, true)
                        bundle.putLong(KEY_CITY_ID, weather.id!!)
                        fragment.arguments = bundle
                        val nav = findNavController()
                        nav.navigate(R.id.weatherAddFragment, bundle)
                        cardSearchCity.visibility = View.GONE
                    }
                }
            }
        })
    }
}
