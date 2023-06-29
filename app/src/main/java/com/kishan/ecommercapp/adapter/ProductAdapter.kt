package com.kishan.ecommercapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kishan.ecommercapp.activity.ProductDetailsActivity
import com.kishan.ecommercapp.databinding.LayoutProductItemBinding
import com.kishan.ecommercapp.model.AddProductModel

class ProductAdapter(val context:Context, val list:ArrayList<AddProductModel>) : RecyclerView.Adapter<ProductAdapter.ProductAdapterViewHolder>() {

    inner class ProductAdapterViewHolder(val binding: LayoutProductItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapterViewHolder {
        val binding = LayoutProductItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ProductAdapterViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ProductAdapterViewHolder, position: Int) {
        val data = list[position]

        Glide.with(context).load(data.productCoverImg).into(holder.binding.imageView2)
        holder.binding.textView.text = data.productName
        holder.binding.textView2.text = data.productMrp
        holder.binding.textView3.text = data.productCategory

        holder.binding.button.text = "Rs. ${data.productSp}"
        holder.binding.button2.text = "Add to Cart"

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailsActivity::class.java)
            intent.putExtra("id", data.productId)
            context.startActivity(intent)
        }
    }
}