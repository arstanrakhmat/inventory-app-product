package com.example.inventoryapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.inventoryapp.data.Product
import com.example.inventoryapp.databinding.CustomRowBinding
import com.example.inventoryapp.fragments.MainFragmentDirections

class InventoryAdapter(private val listeners: RecyclerListeners) :
    RecyclerView.Adapter<InventoryAdapter.ViewHolder>() {
    private var list = emptyList<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CustomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val oneUser = list[position]

        holder.myImage.loadImage(oneUser.image)
        holder.myProdName.text = oneUser.nameProduct
        holder.myPrice.text = oneUser.price.toString()
        holder.myCompanyName.text = oneUser.ownerProduct
        holder.myAmount.text = oneUser.amountPr.toString()

        holder.rowLayout.setOnClickListener {
            val action = MainFragmentDirections.actionMainPageFragmentToUpdateFragment(oneUser)
            holder.itemView.findNavController().navigate(action)
        }

        holder.dots.setOnClickListener { listeners.archiveProduct(oneUser) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(product: List<Product>) {
        this.list = product
        notifyDataSetChanged()
    }

    class ViewHolder(binding: CustomRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val myImage: ImageView = binding.photoCross
        val myProdName: TextView = binding.customRowName
        val myPrice: TextView = binding.price
        val myCompanyName: TextView = binding.companyName
        val myAmount: TextView = binding.productCount

        val rowLayout: ConstraintLayout = binding.rowLayout
        val dots: ImageView = binding.dotsVertical
    }
}