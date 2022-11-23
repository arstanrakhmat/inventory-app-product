package com.example.inventoryapp.fragments

import android.app.Activity
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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.drawToBitmap
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.inventoryapp.R
import com.example.inventoryapp.data.Product
import com.example.inventoryapp.data.ProductApplication
import com.example.inventoryapp.databinding.FragmentUpdateBinding
import com.example.inventoryapp.loadImage
import com.example.inventoryapp.viewModelAdd.AddViewModel
import com.example.inventoryapp.viewModelAdd.AddViewModelFactory

class UpdateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding

    private val mViewModel by viewModels<AddViewModel> {
        AddViewModelFactory((requireActivity().application as ProductApplication).repository)
    }

    private val args by navArgs<UpdateFragmentArgs>()

    private var bitmap: Bitmap? = null

    private val getContent =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            if (bitmap != null) {
                binding.updatePhoto.loadImage(bitmap)
                this.bitmap = bitmap
            }
        }

//    private val requestSinglePermissionLauncher =
//        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
//            if (isGranted) {
//                getContent.launch()
//            } else {
//                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
//            }
//        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentUpdateBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        openCard()

        binding.apply {
            cancelBtn.setOnClickListener {
                findNavController().navigate(R.id.action_updateFragment_to_mainPageFragment)
            }
            saveProductBtn.setOnClickListener {
                updateItem()
            }

            binding.updatePhoto.setOnClickListener {
                pickImageGallery()
            }

            binding.back.setOnClickListener {
                navigateUp()
            }
        }
    }

    private fun navigateUp() {
        findNavController().navigateUp()
    }

    private fun openCard() {
        binding.apply {
            updatePhoto.setImageBitmap(args.curProduct.image)
            updateNameProduct.setText(args.curProduct.nameProduct)
            updatePriceProduct.setText(args.curProduct.price.toString())
            updateOwnerProduct.setText(args.curProduct.ownerProduct)
            updateAmountProduct.setText(args.curProduct.amountPr.toString())
        }
    }

    private fun updateItem() {
        val photo = binding.updatePhoto
        val prName = binding.updateNameProduct.text.toString()
        val price = binding.updatePriceProduct.text
        val owner = binding.updateOwnerProduct.text.toString()
        val amount = binding.updateAmountProduct.text

        if (inputCheck(prName, price, owner, amount)) {
            val product = Product(
                args.curProduct.id,
                photo.drawToBitmap(),
                prName,
                price.toString().toDouble(),
                owner,
                amount.toString().toInt(),
                args.curProduct.isArchived
            )

            mViewModel.updateData(product)
            Toast.makeText(requireContext(), "Updated suc", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_mainPageFragment)
        }
    }

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, AddFragment.IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AddFragment.IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val path: Uri? = data?.data
            binding.updatePhoto.setImageURI(path)
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