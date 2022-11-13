package com.example.inventoryapp

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
import androidx.navigation.fragment.navArgs
import com.example.inventoryapp.data.Product
import com.example.inventoryapp.data.ProductApplication
import com.example.inventoryapp.databinding.FragmentUpdateBinding
import com.example.inventoryapp.viewModelAddFragment.AddFragmentViewModel
import com.example.inventoryapp.viewModelAddFragment.AddFragmentViewModelFactory

class UpdateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding

    private val mViewModel by viewModels<AddFragmentViewModel> {
        AddFragmentViewModelFactory((requireActivity().application as ProductApplication).repository)
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
                requestSinglePermissionLauncher.launch(android.Manifest.permission.CAMERA)
            }
        }
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

        val prName = binding.updateNameProduct.text.toString()
        val price = binding.updatePriceProduct.text
        val owner = binding.updateOwnerProduct.text.toString()
        val amount = binding.updateAmountProduct.text

        if (inputCheck(prName, price, owner, amount)) {
            val product = Product(
                args.curProduct.id,
                bitmap!!,
                prName,
                price.toString().toDouble(),
                owner,
                amount.toString().toInt()
            )

            mViewModel.updateData(product)
            Toast.makeText(requireContext(), "Updated suc", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_mainPageFragment)
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