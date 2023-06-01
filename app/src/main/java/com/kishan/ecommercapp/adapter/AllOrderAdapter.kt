package com.kishan.ecommercapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kishan.ecommercapp.databinding.AllOrderLayoutBinding
import com.kishan.ecommercapp.model.AllOrderedModel

class AllOrderAdapter(val list:ArrayList<AllOrderedModel>, val context: Context) : RecyclerView.Adapter<AllOrderAdapter.AllOrderViewHolder>() {

    inner class AllOrderViewHolder(val binding: AllOrderLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllOrderViewHolder {
        return AllOrderViewHolder(
            AllOrderLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AllOrderViewHolder, position: Int) {
        val data = list[position]
        holder.binding.productNameTv.text = data.name
        holder.binding.productPriceTv.text = data.sellingprice

        when (data.status) {
            "Ordered" -> {
                holder.binding.productStatusTv.text = "Ordered"
            }
            "Dispatched" -> {
                holder.binding.productStatusTv.text = "Dispatched"
            }
            "Delivered" -> {
                holder.binding.productStatusTv.text = "Delivered"
            }
            "Canceled" -> {
                holder.binding.productStatusTv.text = "Canceled"
            }
        }
    }
}