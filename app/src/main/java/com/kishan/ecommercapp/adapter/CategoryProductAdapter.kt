package com.kishan.ecommercapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kishan.ecommercapp.activity.ProductDetailsActivity
import com.kishan.ecommercapp.databinding.ItemCategoryLayoutBinding
import com.kishan.ecommercapp.model.AddProductModel

class CategoryProductAdapter(val context:Context, val list : ArrayList<AddProductModel>) : RecyclerView.Adapter<CategoryProductAdapter.CategoryProductViewHolder>() {

    inner class CategoryProductViewHolder( val binding: ItemCategoryLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryProductViewHolder {
        val binding = ItemCategoryLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return  CategoryProductViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: CategoryProductViewHolder, position: Int) {
        val data = list[position]
        Glide.with(context).load(data.productCoverImg).into(holder.binding.imageView3)
        holder.binding.textView4.text = data.productName
        holder.binding.textView5.text = data.productSp

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailsActivity::class.java)
            intent.putExtra("id", data.productId)
            context.startActivity(intent)
        }
    }
}