package com.example.inventoryapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.inventoryapp.R
import com.example.inventoryapp.RecyclerListAdapter
import com.example.inventoryapp.data.ProductApplication
import com.example.inventoryapp.databinding.FragmentMainPageBinding
import com.example.inventoryapp.viewModelMainFragment.MainFragmentViewModel
import com.example.inventoryapp.viewModelMainFragment.MainFragmentViewModelFactory

class MainPageFragment : Fragment() {

    private lateinit var binding: FragmentMainPageBinding
    private val mViewModel by viewModels<MainFragmentViewModel> {
        MainFragmentViewModelFactory((requireActivity().application as ProductApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMainPageBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addBtn.setOnClickListener {
            addProduct()
        }

        val adapter = RecyclerListAdapter()
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        mViewModel.getAllData().observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
    }

    private fun addProduct() {
        findNavController().navigate(R.id.action_mainPageFragment_to_addProductFragment)
    }
}