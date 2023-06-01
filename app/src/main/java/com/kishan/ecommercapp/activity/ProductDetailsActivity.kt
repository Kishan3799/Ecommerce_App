package com.kishan.ecommercapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kishan.ecommercapp.MainActivity
import com.kishan.ecommercapp.R
import com.kishan.ecommercapp.data.roomdb.AppDataBase
import com.kishan.ecommercapp.data.roomdb.Product
import com.kishan.ecommercapp.data.roomdb.ProductDao
import com.kishan.ecommercapp.databinding.ActivityProductDetailsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProductDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)

        getProductDetail(intent.getStringExtra("id"))
        setContentView(binding.root)
    }

    private fun getProductDetail(productId: String?) {
        Firebase.firestore.collection("products")
            .document(productId!!).get().addOnSuccessListener {
                val list = it.get("productImages") as ArrayList<String>
                binding.productTitle.text = it.getString("productName")
                binding.productSellingPriceTv.text = it.getString("productSp")
                binding.productDescriptionTv.text = it.getString("productDescription")

                val slideList = ArrayList<SlideModel>()
                for (data in list){
                    slideList.add(SlideModel(data, ScaleTypes.CENTER_CROP))
                }

                cartAction(productId, it.getString("productName"), it.getString("productSp"), it.getString("productCoverImg"))
                binding.imageSlider.setImageList(slideList)
            }.addOnFailureListener {
                Toast.makeText(this, "Something went Wrong", Toast.LENGTH_SHORT).show()
            }
    }

    private fun cartAction(productId: String, productName: String?, productSP: String?, coverImage: String?) {
        val productDao = AppDataBase.getInstance(this).productDao()

        if(productDao.isProductExist(productId) != null) {
            binding.addToCartBtn.text = "Go to Cart"
        }else {
            binding.addToCartBtn.text = "Add to Cart"
        }

        binding.addToCartBtn.setOnClickListener {
            if(productDao.isProductExist(productId) != null) {
                openCart()
            }else {
                addToCart(productDao, productId, productName, productSP, coverImage)
            }
        }
    }

    private fun addToCart(
        productDao: ProductDao,
        productId: String,
        productName: String?,
        productSP: String?,
        coverImage: String?,
    ) {
        val data = Product(productId,productName,coverImage,productSP)
        lifecycleScope.launch(Dispatchers.IO) {
            productDao.insertProduct(data)
            binding.addToCartBtn.text = "Go to Cart"
        }
    }

    private fun openCart() {
        val preferences = this.getSharedPreferences("info" , MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean("isCart", true)
        editor.apply()

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}