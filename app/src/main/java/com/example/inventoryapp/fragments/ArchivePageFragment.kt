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
import com.example.inventoryapp.RecyclerListAdapter
import com.example.inventoryapp.RecyclerListeners
import com.example.inventoryapp.data.Product
import com.example.inventoryapp.data.ProductApplication
import com.example.inventoryapp.databinding.FragmentArchivePageBinding
import com.example.inventoryapp.viewModelArchiveFragment.ArchiveFragmentViewModel
import com.example.inventoryapp.viewModelArchiveFragment.ArchiveFragmentViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior

class ArchivePageFragment : Fragment(), RecyclerListeners {

    private lateinit var binding: FragmentArchivePageBinding

    private val mViewModel by viewModels<ArchiveFragmentViewModel> {
        ArchiveFragmentViewModelFactory((requireActivity().application as ProductApplication).repository)
    }

    private lateinit var myAdapter: RecyclerListAdapter

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

        myAdapter = RecyclerListAdapter(this)
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
            tv1.text = "Востановить"
            tv2.apply {
                text = "Удалить"
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
            setPositiveButton("Удалить") { p0, _ ->
                mViewModel.deleteProduct(product.id)
                p0.dismiss()
                Toast.makeText(requireContext(), "Удалено", Toast.LENGTH_SHORT).show()
            }
            setNegativeButton("Нет") { p0, _ ->
                p0.dismiss()
            }
            create()
            show()
        }
    }

    private fun logicForAlertDialog2(product: Product) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Востановить ${product.nameProduct} из архива?")
            setPositiveButton("Да") { p0, _ ->
                mViewModel.unArchiveData(product)
                p0.dismiss()
                Toast.makeText(requireContext(), "Востановлено", Toast.LENGTH_SHORT).show()
            }
            setNegativeButton("Нет") { p0, _ ->
                p0.dismiss()
            }
            create()
            show()
        }
    }
}