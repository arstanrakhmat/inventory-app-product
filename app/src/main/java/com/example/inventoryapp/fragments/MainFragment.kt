package com.example.inventoryapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.inventoryapp.R
import com.example.inventoryapp.InventoryAdapter
import com.example.inventoryapp.RecyclerListeners
import com.example.inventoryapp.data.Product
import com.example.inventoryapp.data.ProductApplication
import com.example.inventoryapp.databinding.FragmentMainPageBinding
import com.example.inventoryapp.viewModelMain.MainViewModel
import com.example.inventoryapp.viewModelMain.MainViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MainFragment : Fragment(), RecyclerListeners {

    private lateinit var binding: FragmentMainPageBinding
    private val mViewModel by viewModels<MainViewModel> {
        MainViewModelFactory((requireActivity().application as ProductApplication).repository)
    }

    private lateinit var myAdapter: InventoryAdapter

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

//        val adapter = RecyclerListAdapter()
        myAdapter = InventoryAdapter(this)
        val recyclerView = binding.recyclerView
        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        mViewModel.getAllData(false).observe(viewLifecycleOwner) {
            myAdapter.setData(it)
        }
        searchViewSet()
    }

    private fun searchViewSet() {
        binding.searchView.apply {
            isSubmitButtonEnabled = true
            setOnQueryTextListener(object :
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) {
                        dataObserve(query)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) {
                        dataObserve(newText)
                    }
                    return true
                }
            })
        }
    }

    private fun dataObserve(querySearch: String) {
        val searchQuery = "%$querySearch%"

        mViewModel.getAllSearchProduct(searchQuery).observe(viewLifecycleOwner) {
            it.let {
                myAdapter.setData(it)
            }
        }
    }

    override fun archiveProduct(product: Product) {
        val bottomAlert = BottomSheetBehavior.from(binding.includeMainFragment.bottomAlert)
        bottomAlert.state = BottomSheetBehavior.STATE_EXPANDED

        binding.includeMainFragment.tv1.setOnClickListener {
            bottomAlert.state = BottomSheetBehavior.STATE_HIDDEN
            logicForAlertDialog(product)
        }
    }

    private fun logicForAlertDialog(product: Product) {
        AlertDialog.Builder(requireActivity()).apply {
            setTitle("Архивировать ${product.nameProduct} из каталога")
            setPositiveButton("Да") { _, _ ->
                mViewModel.updateData(
                    Product(
                        product.id,
                        product.image,
                        product.nameProduct,
                        product.price,
                        product.ownerProduct,
                        product.amountPr,
                        true
                    )
                )
                Toast.makeText(requireContext(), "Заархивировано", Toast.LENGTH_SHORT).show()
            }
            setNegativeButton("Нет") { p0, _ ->
                p0.dismiss()
            }

            create()
            show()
        }
    }

    private fun addProduct() {
        findNavController().navigate(R.id.action_mainPageFragment_to_addProductFragment)
    }
}