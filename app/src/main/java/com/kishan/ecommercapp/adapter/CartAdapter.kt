package com.kishan.ecommercapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kishan.ecommercapp.activity.ProductDetailsActivity
import com.kishan.ecommercapp.data.roomdb.AppDataBase
import com.kishan.ecommercapp.data.roomdb.Product
import com.kishan.ecommercapp.databinding.LayoutCartItemBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CartAdapter(val context:Context , val list : List<Product>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(val binding: LayoutCartItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = LayoutCartItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return CartViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val data = list[position]

        Glide.with(context).load(data.productImage).into(holder.binding.imageView4)

        holder.binding.textView6.text = data.productName
        holder.binding.textView7.text = data.productSellingPrice

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailsActivity::class.java)
            intent.putExtra("id" , data.productId)
            context.startActivity(intent)
        }

        val dao = AppDataBase.getInstance(context).productDao()
        holder.binding.deleteBtn.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                dao.deleteProduct(product = Product(data.productId, data.productName, data.productImage, data.productSellingPrice))
            }
        }
    }
}