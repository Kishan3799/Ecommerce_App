package com.kishan.ecommercapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.kishan.ecommercapp.R
import com.kishan.ecommercapp.activity.AddressActivity
import com.kishan.ecommercapp.adapter.CartAdapter
import com.kishan.ecommercapp.data.roomdb.AppDataBase
import com.kishan.ecommercapp.data.roomdb.Product
import com.kishan.ecommercapp.databinding.FragmentCartBinding

class CartFragment : Fragment() {
    private lateinit var binding : FragmentCartBinding
    private lateinit var list: ArrayList<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentCartBinding.inflate(layoutInflater)

        val preferences = requireContext().getSharedPreferences("info" , AppCompatActivity.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean("isCart", false)
        editor.apply()
        // Inflate the layout for this fragment

        val dao = AppDataBase.getInstance(requireContext()).productDao()
        list = ArrayList()
        dao.getAllProduct().observe(requireActivity()){
            binding.recyclerView2.adapter = CartAdapter(requireContext(), it)
            list.clear()
            for (data in it){
                list.add(data.productId)
            }
            totalCost(it)
        }

        return binding.root
    }

    private fun totalCost(data: List<Product>?) {
        var total = 0
        for (item in data!!){
            total += item.productSellingPrice!!.toInt()
        }

        binding.tvTotalItem.text = "Total item in cart is ${data.size}"
        binding.tvTotal.text = "Total Cost : $total"

        binding.checkout.setOnClickListener {
            val intent = Intent(requireContext(), AddressActivity::class.java)
            val b = Bundle()
            b.putStringArrayList("productIds", list)
            b.putString("totalCost", total.toString())
            intent.putExtras(b)
            startActivity(intent)
        }
    }


}