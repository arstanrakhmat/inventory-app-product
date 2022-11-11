package com.example.inventoryapp.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.inventoryapp.R
import com.example.inventoryapp.data.Product
import com.example.inventoryapp.data.ProductViewModel
import com.example.inventoryapp.databinding.FragmentAddProductBinding

class AddProductFragment : Fragment() {

    private lateinit var mProductViewModel: ProductViewModel
    private lateinit var binding: FragmentAddProductBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAddProductBinding.inflate(inflater, container, false)

        mProductViewModel = ViewModelProvider(this)[ProductViewModel::class.java]

        clickListeners()

        return binding.root
    }

    private fun clickListeners() {
        binding.cancelBtn.setOnClickListener {
            findNavController().navigate(R.id.action_addProductFragment_to_mainPageFragment)
        }

        binding.addProductBtn.setOnClickListener {
            insertDataToDatabase()
        }
    }

    private fun insertDataToDatabase() {
        val productName = binding.nameProduct.text.toString()
        val priceProduct = binding.priceProduct.text
        val productOwner = binding.ownerProduct.text.toString()
        val amountProduct = binding.amountProduct.text

        if (inputCheck(productName, priceProduct, productOwner, amountProduct)) {
            val product = Product(
                0,
                productName,
                priceProduct.toString().toDouble(),
                productOwner,
                amountProduct.toString().toInt()
            )
            mProductViewModel.addProduct(product)
            Toast.makeText(requireContext(), "Fields are filled", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addProductFragment_to_mainPageFragment)
        } else {
            Toast.makeText(requireContext(), "Fill out all fields", Toast.LENGTH_SHORT).show()
        }

    }

    private fun inputCheck(
        productName: String,
        price: Editable,
        owner_name: String,
        amount_product: Editable
    ): Boolean {
        return !(TextUtils.isEmpty(productName) &&
                TextUtils.isEmpty(price) &&
                TextUtils.isEmpty(owner_name) &&
                TextUtils.isEmpty(amount_product))
    }

}