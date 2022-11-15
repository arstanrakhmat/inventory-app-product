package com.example.inventoryapp.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.inventoryapp.R
import com.example.inventoryapp.data.Product
import com.example.inventoryapp.data.ProductApplication
import com.example.inventoryapp.databinding.FragmentAddProductBinding
import com.example.inventoryapp.loadImage
import com.example.inventoryapp.viewModelAddFragment.AddFragmentViewModel
import com.example.inventoryapp.viewModelAddFragment.AddFragmentViewModelFactory

class AddProductFragment : Fragment() {

    private lateinit var binding: FragmentAddProductBinding

    private val mViewModel by viewModels<AddFragmentViewModel> {
        AddFragmentViewModelFactory((requireActivity().application as ProductApplication).repository)
    }

    private var bitmap: Bitmap? = null

    private val getContent =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            if (bitmap != null) {
                binding.photo.loadImage(bitmap)
                this.bitmap = bitmap
            }
        }

    private val requestSinglePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getContent.launch()
            } else {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAddProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clickListeners()
    }

    private fun clickListeners() {
        binding.cancelBtn.setOnClickListener {
            findNavController().navigate(R.id.action_addProductFragment_to_mainPageFragment)
        }

        binding.addProductBtn.setOnClickListener {
            insertDataToDatabase()
        }

        binding.photo.setOnClickListener {
            requestSinglePermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }

        binding.back.setOnClickListener {

            navigateUp()
        }
    }

    private fun navigateUp() {
        findNavController().navigateUp()
    }

    private fun insertDataToDatabase() {
        val productName = binding.nameProduct.text.toString()
        val priceProduct = binding.priceProduct.text
        val productOwner = binding.ownerProduct.text.toString()
        val amountProduct = binding.amountProduct.text

        if (inputCheck(productName, priceProduct, productOwner, amountProduct)) {
            val product = Product(
                0,
                bitmap!!,
                productName,
                priceProduct.toString().toDouble(),
                productOwner,
                amountProduct.toString().toInt(),
                false
            )
            mViewModel.addData(product)
//            Toast.makeText(requireContext(), "Fields are filled", Toast.LENGTH_SHORT).show()
//            findNavController().navigate(R.id.action_addProductFragment_to_mainPageFragment)
        } else {
            Toast.makeText(requireContext(), "Fill out all fields", Toast.LENGTH_SHORT).show()
        }

        mViewModel.isDataSaved.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    Toast.makeText(requireContext(), "Fields are filled", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_addProductFragment_to_mainPageFragment)
                }
            }
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