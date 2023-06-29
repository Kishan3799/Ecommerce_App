package com.kishan.ecommercapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kishan.ecommercapp.R
import com.kishan.ecommercapp.adapter.CategoryProductAdapter
import com.kishan.ecommercapp.adapter.ProductAdapter
import com.kishan.ecommercapp.model.AddProductModel

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        //show product related to categories
        getProducts(intent.getStringExtra("cat"))
    }

    private fun getProducts(category:String?){
        val list = ArrayList<AddProductModel>()
        //match category with product category
        Firebase.firestore.collection("products").whereEqualTo("productCategory",category)
            .get().addOnSuccessListener {
                list.clear()
                for(doc in it.documents){
                    val data = doc.toObject(AddProductModel::class.java)
                    list.add(data!!)
                }
                val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                recyclerView.adapter = CategoryProductAdapter(this,list)
            }
    }
}