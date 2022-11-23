package com.example.inventoryapp.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.core.view.drawToBitmap
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.inventoryapp.R
import com.example.inventoryapp.data.Product
import com.example.inventoryapp.data.ProductApplication
import com.example.inventoryapp.databinding.FragmentAddProductBinding
import com.example.inventoryapp.loadImage
import com.example.inventoryapp.viewModelAdd.AddViewModel
import com.example.inventoryapp.viewModelAdd.AddViewModelFactory

class AddFragment : Fragment() {

    companion object {
        val IMAGE_REQUEST_CODE = 100
    }

    private lateinit var binding: FragmentAddProductBinding

    private val mViewModel by viewModels<AddViewModel> {
        AddViewModelFactory((requireActivity().application as ProductApplication).repository)
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
            pickImageGallery()
        }

        binding.back.setOnClickListener {
            navigateUp()
        }
    }

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            val path: Uri? = data?.data
            binding.photo.setImageURI(path)
        }
    }

    private fun navigateUp() {
        findNavController().navigateUp()
    }

    private fun insertDataToDatabase() {
        val productImage = binding.photo
        val productName = binding.nameProduct.text.toString()
        val priceProduct = binding.priceProduct.text
        val productOwner = binding.ownerProduct.text.toString()
        val amountProduct = binding.amountProduct.text

        if (inputCheck(
                productImage,
                productName,
                priceProduct,
                productOwner,
                amountProduct
            )
        ) {
            val product = Product(
                0,
                productImage.drawToBitmap(),
                productName,
                priceProduct.toString().toDouble(),
                productOwner,
                amountProduct.toString().toInt(),
                false
            )
            mViewModel.addData(product)

        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.fillOutAllFields),
                Toast.LENGTH_SHORT
            ).show()
        }

        mViewModel.isDataSaved.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.fieldsAreField),
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().navigate(R.id.action_addProductFragment_to_mainPageFragment)
                }
            }
        }
    }

    private fun inputCheck(
        image: ImageView,
        productName: String,
        price: Editable,
        owner_name: String,
        amount_product: Editable
    ): Boolean {
        return !(image.drawable == null &&
                TextUtils.isEmpty(productName) &&
                TextUtils.isEmpty(price) &&
                TextUtils.isEmpty(owner_name) &&
                TextUtils.isEmpty(amount_product))
    }

}