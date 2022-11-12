package com.example.inventoryapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.inventoryapp.data.Product
import com.example.inventoryapp.fragments.MainPageFragmentDirections

class RecyclerListAdapter : RecyclerView.Adapter<RecyclerListAdapter.ViewHolder>() {
    private var list = emptyList<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val oneUser = list[position]

        holder.myId.text = oneUser.id.toString()
        holder.myProdName.text = oneUser.nameProduct
        holder.myPrice.text = oneUser.price.toString()
        holder.myCompanyName.text = oneUser.ownerProduct
        holder.myAmount.text = oneUser.amountPr.toString()

        holder.rowLayout.setOnClickListener {
            val action = MainPageFragmentDirections.actionMainPageFragmentToUpdateFragment(oneUser)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(product: List<Product>) {
        this.list = product
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val myId: TextView = itemView.findViewById(R.id.photoCross)
        val myProdName: TextView = itemView.findViewById(R.id.customRowName)
        val myPrice: TextView = itemView.findViewById(R.id.price)
        val myCompanyName: TextView = itemView.findViewById(R.id.companyName)
        val myAmount: TextView = itemView.findViewById(R.id.productCount)

        val rowLayout: ConstraintLayout = itemView.findViewById(R.id.rowLayout)
    }
}