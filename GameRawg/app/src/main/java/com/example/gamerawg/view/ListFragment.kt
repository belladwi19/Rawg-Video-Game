package com.example.gamerawg.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gamerawg.data.model.GameList
import com.example.gamerawg.databinding.FragmentListBinding
import com.example.gamerawg.view.adapter.GameAdapter
import com.example.gamerawg.view.adapter.PlatformAdapter
import com.example.gamerawg.viewmodel.ListViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {
    lateinit var binding: FragmentListBinding
    private var isFirst = true
    private lateinit var gameAdapter: GameAdapter
    private lateinit var platformAdapter: PlatformAdapter
    private val viewModel: ListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater)
        return binding.root
    }

    private fun getData() {
        val listType = object : TypeToken<ArrayList<GameList>>() {}.type
        val platformsType = object : TypeToken<ArrayList<GameList>>() {}.type

        viewModel.nextUrl.value = activity!!.intent.getStringExtra("nextPageUrl")
        viewModel.list.value =
            Gson().fromJson(activity!!.intent.getStringExtra("firstList"), listType)
        viewModel.platform.value =
            Gson().fromJson(activity!!.intent.getStringExtra("games"), platformsType)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupComponents() {

        gameAdapter = GameAdapter()
        binding.rvGameCard.layoutManager = GridLayoutManager(context, 2)
        binding.rvGameCard.adapter = gameAdapter

        binding.btnClear.visibility = View.GONE
        binding.progressBar.visibility = View.GONE

        binding.rvGameCard.setOnTouchListener(View.OnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                if (!binding.rvGameCard.canScrollVertically(1)) {
                    viewModel.getDatasNext(context!!)
                }
            }
            false
        })

        binding.searchList.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                viewModel.setSearchValues(p0)
                viewModel.getList()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })

        binding.btnClear.setOnClickListener {
            viewModel.clearSearch()
            binding.searchList.setQuery("", false)
            viewModel.getList()
        }
    }

    fun observeLiveData() {
        viewModel.list.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.size == 0) {
                    Toast.makeText(activity, "Tidak Ada Data Yang Ditampilkan", Toast.LENGTH_SHORT)
                        .show();
                    gameAdapter.submitList(it as List<GameList>?)
                } else {
                    gameAdapter.submitList(it as List<GameList>?)
                }
            }
        })

        viewModel.itemPosition.observe(viewLifecycleOwner, Observer {
            it?.let {
                platformAdapter.itemList(it)
            }
        })

        viewModel.platform.observe(viewLifecycleOwner, Observer {
            it?.let {
                platformAdapter.updateList(it)
            }
        })

        viewModel.progressBar.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    binding.progressBar.visibility = View.VISIBLE
                }
                if (!it) {
                    binding.progressBar.visibility = View.GONE
                }
            }
        })

        viewModel.search.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it != "") {
                    binding.btnClear.visibility = View.VISIBLE
                } else {
                    binding.btnClear.visibility = View.GONE
                }
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    binding.rvGameCard.visibility = View.GONE
                }
                if (!it) {
                    binding.rvGameCard.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        platformAdapter = PlatformAdapter(arrayListOf())
        setupComponents()
        observeLiveData()
        if (isFirst) {
            isFirst = false
            getData()
        }
    }
}