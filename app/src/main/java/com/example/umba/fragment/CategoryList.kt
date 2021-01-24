package com.example.umba.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.umba.R
import com.example.umba.databinding.FragmentCategoryListBinding
import com.example.umba.repository.network.Connetion

class CategoryList : Fragment() {

    private lateinit var _binding : FragmentCategoryListBinding
    val binding  get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.latest.setOnClickListener{
            val action = CategoryListDirections.actionCategoryListToHome2("1")
            view.findNavController().navigate(action)
        }
        binding.upcoming.setOnClickListener{
            val action = CategoryListDirections.actionCategoryListToHome2("3")
            view.findNavController().navigate(action)
        }
        binding.popular.setOnClickListener{
            val action = CategoryListDirections.actionCategoryListToHome2("2")
            view.findNavController().navigate(action)
        }
        displayDialog()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category_list, container, false)
        _binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun displayDialog() {
        if (!Connetion.isConnected(requireContext())) {
            AlertDialog.Builder(requireContext())
                .setMessage("Retry Download")
                .setCancelable(false)
                .setPositiveButton("Retry") { _, _ ->
                    displayDialog()
                }
                .setNegativeButton("No", null)
                .show()
        }
    }

}