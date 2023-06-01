package com.kishan.ecommercapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kishan.ecommercapp.R
import com.kishan.ecommercapp.activity.CategoryActivity
import com.kishan.ecommercapp.databinding.LayoutCategoryItemBinding
import com.kishan.ecommercapp.model.CategoryModel

class CategoryAdapter(private val context:Context, private val list: ArrayList<CategoryModel>) :RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val binding = LayoutCategoryItemBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return  CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_category_item, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.binding.categoryTv.text = list[position].category
        Glide.with(context).load(list[position].img).into(holder.binding.imageView)

        holder.itemView.setOnClickListener{
            val intent = Intent(context, CategoryActivity::class.java)
            intent.putExtra("cat", list[position].category)
            context.startActivity(intent)
        }
    }
}