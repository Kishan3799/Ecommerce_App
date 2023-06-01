package com.kishan.ecommercapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kishan.ecommercapp.R
import com.kishan.ecommercapp.adapter.AllOrderAdapter
import com.kishan.ecommercapp.databinding.FragmentMoreBinding
import com.kishan.ecommercapp.model.AllOrderedModel


class MoreFragment : Fragment() {

    private lateinit var binding: FragmentMoreBinding
    private lateinit var list : ArrayList<AllOrderedModel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMoreBinding.inflate(layoutInflater)
        list = ArrayList()

        val preferences = requireContext().getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
        Firebase.firestore.collection("allOrders").whereEqualTo("userId", preferences.getString("number", "")!!)
            .get().addOnSuccessListener {
                list.clear()
                for(doc in it){
                    val data = doc.toObject(AllOrderedModel::class.java)
                    list.add(data)
                }
                binding.recyclerViewAllOrdered.adapter = AllOrderAdapter(list, requireContext() )
            }

        return binding.root
    }


}