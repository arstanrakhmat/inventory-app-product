package com.example.inventoryapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.inventoryapp.R
import com.example.inventoryapp.InventoryAdapter
import com.example.inventoryapp.RecyclerListeners
import com.example.inventoryapp.data.Product
import com.example.inventoryapp.data.ProductApplication
import com.example.inventoryapp.databinding.FragmentArchivePageBinding
import com.example.inventoryapp.viewModelArchive.ArchiveViewModel
import com.example.inventoryapp.viewModelArchive.ArchiveViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior

class ArchiveFragment : Fragment(), RecyclerListeners {

    private lateinit var binding: FragmentArchivePageBinding

    private val mViewModel by viewModels<ArchiveViewModel> {
        ArchiveViewModelFactory((requireActivity().application as ProductApplication).repository)
    }

    private lateinit var myAdapter: InventoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentArchivePageBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myAdapter = InventoryAdapter(this)
        val recyclerView = binding.recyclerViewInArchive
        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        mViewModel.archiveProducts.observe(viewLifecycleOwner) {
            it.let {
                myAdapter.setData(it)
            }
        }

        bottomAlertSettings()
        searchViewSet()
    }

    private fun searchViewSet() {
        binding.searchViewInArchive.apply {
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

        mViewModel.getSearchArchiveProduct(searchQuery).observe(viewLifecycleOwner) {
            it.let {
                myAdapter.setData(it)
            }
        }
    }

    private fun bottomAlertSettings() {
        with(binding.include) {
            tv1.text = getString(R.string.reestablish)
            tv2.apply {
                text = getString(R.string.delete)
                visibility = View.VISIBLE
            }
        }
    }

    override fun archiveProduct(product: Product) {
        val bottomAlert = BottomSheetBehavior.from(binding.include.bottomAlert)
        bottomAlert.state = BottomSheetBehavior.STATE_EXPANDED

        binding.include.tv1.setOnClickListener {
            bottomAlert.state = BottomSheetBehavior.STATE_HIDDEN
            logicForAlertDialog2(product)
        }

        binding.include.tv2.setOnClickListener {
            bottomAlert.state = BottomSheetBehavior.STATE_HIDDEN
            logicForAlertDialog1(product)
        }
    }

    private fun logicForAlertDialog1(product: Product) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Удалить ${product.nameProduct} из архива?")
            setPositiveButton(getString(R.string.delete)) { p0, _ ->
                mViewModel.deleteProduct(product.id)
                p0.dismiss()
                Toast.makeText(requireContext(), getString(R.string.deleted), Toast.LENGTH_SHORT)
                    .show()
            }
            setNegativeButton(getString(R.string.no)) { p0, _ ->
                p0.dismiss()
            }
            create()
            show()
        }
    }

    private fun logicForAlertDialog2(product: Product) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Востановить ${product.nameProduct} из архива?")
            setPositiveButton(getString(R.string.yes)) { p0, _ ->
                mViewModel.unArchiveData(product)
                p0.dismiss()
                Toast.makeText(
                    requireContext(),
                    getString(R.string.reestablished),
                    Toast.LENGTH_SHORT
                ).show()
            }
            setNegativeButton(getString(R.string.no)) { p0, _ ->
                p0.dismiss()
            }
            create()
            show()
        }
    }
}