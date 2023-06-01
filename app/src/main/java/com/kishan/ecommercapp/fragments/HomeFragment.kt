package com.kishan.ecommercapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kishan.ecommercapp.R
import com.kishan.ecommercapp.adapter.CategoryAdapter
import com.kishan.ecommercapp.adapter.ProductAdapter
import com.kishan.ecommercapp.databinding.FragmentHomeBinding
import com.kishan.ecommercapp.model.AddProductModel
import com.kishan.ecommercapp.model.CategoryModel


class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        val preferences = requireContext().getSharedPreferences("info" , AppCompatActivity.MODE_PRIVATE)

        if (preferences.getBoolean("isCart", false))
            findNavController().navigate(R.id.action_homeFragment_to_cartFragment)

        getCategories()

        getProducts()

        getSliderImages()

        return binding.root
    }

    private fun getSliderImages() {
        Firebase.firestore.collection("slider").document("item").get()
            .addOnSuccessListener {
                Glide.with(requireContext()).load(it.get("img")).into(binding.sliderImage)
            }
    }

    private fun getProducts() {
        val productList = ArrayList<AddProductModel>()
        Firebase.firestore.collection("products")
            .get().addOnSuccessListener {
                productList.clear()
                for(doc in it.documents){
                    val data = doc.toObject(AddProductModel::class.java)
                    productList.add(data!!)
                }
                  binding.productRv.adapter = ProductAdapter(requireContext(),productList)
            }
    }

    private fun getCategories() {
        val categoryList = ArrayList<CategoryModel>()
        Firebase.firestore.collection("categories")
            .get().addOnSuccessListener {
                categoryList.clear()
                for(doc in it.documents){
                    val data = doc.toObject(CategoryModel::class.java)
                    categoryList.add(data!!)
                }
                binding.categoryRv.adapter = CategoryAdapter(requireContext(),categoryList)
            }
    }




}