package com.example.umba.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umba.BR
import com.example.umba.BaseApplication
import com.example.umba.R
import com.example.umba.databinding.FragmentHomeBinding
import com.example.umba.fragment.adapter.HomeAdapter
import com.example.umba.repository.database.model.Movie
import com.example.umba.viewModel.HomeViewModel


class Home : Fragment() {
    private lateinit var viewModel: HomeViewModel

    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding

    private lateinit var homeAdapter: HomeAdapter

    private lateinit var categoryId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let{
            categoryId = it.getString(CATEGORY).toString()
        }
        viewModel = makeApiCall()
        homeAdapter = HomeAdapter()
        viewModel.getMovies()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        _binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun makeApiCall(): HomeViewModel {
        val viewModel = HomeViewModel(BaseApplication.application,categoryId)
        viewModel.allMovies.observe(requireActivity(), Observer<List<Movie>> { movie ->
            movie?.let { homeAdapter.submitList(it) }
        })
        return viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.setVariable(BR.viewmodel, viewModel)
        binding.executePendingBindings()
        binding.recyclerView.adapter = homeAdapter
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = GridLayoutManager(context, 3)
    }



    companion object{
        val CATEGORY =  "category"
    }
}