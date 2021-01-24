package com.example.umba.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.umba.BaseApplication
import com.example.umba.R
import com.example.umba.databinding.FragmentMovieDetailBinding
import com.example.umba.repository.network.Connetion
import com.example.umba.viewModel.DetailViewModel
import java.io.File

class MovieDetail : Fragment() {
    private lateinit var viewModel : DetailViewModel

    private lateinit var _binding: FragmentMovieDetailBinding
    private val binding get() = _binding

    private lateinit var movieId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let{
            movieId = it.getString(MOVIE).toString()
        }
        viewModel = makeApiCall()
    }

    private fun makeApiCall() : DetailViewModel{
        val viewModel = DetailViewModel(movieId.toInt(), BaseApplication.application)
        viewModel.movie.observe(requireActivity(),{
            movie -> movie?.let {
                _binding.viewModel = viewModel
                if(Connetion.isConnected(requireContext()) && movie.backdrop_path != null){
                    Glide.with(requireActivity().applicationContext).load("${requireContext().resources.getString(R.string.UrlImage) + viewModel.movie.value?.backdrop_path}").into(binding.movieImageDetail)
                }
                else{
                    val file = File(requireContext().filesDir,"${movie.id.toString() + requireContext().resources.getString(R.string.back)}")
                    if(file.exists()){
                        val bitmap = BitmapFactory.decodeFile(file.toString())
                        binding.movieImageDetail.setImageBitmap(bitmap)
                    }
                    else{
                        binding.movieImageDetail.setImageResource(R.drawable.ic_baseline_wifi_off)
                    }
                }
        }})
        return  viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater,R.layout.fragment_movie_detail, container, false)
        _binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding.viewModel =viewModel
    }

    companion object{
        val MOVIE =  "id_movie"
    }
}