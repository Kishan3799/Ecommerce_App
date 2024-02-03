package com.kishan.ecommercapp.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kishan.ecommercapp.MainActivity
import com.kishan.ecommercapp.R
import com.kishan.ecommercapp.data.roomdb.AppDataBase
import com.kishan.ecommercapp.data.roomdb.Product
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class CheckoutActivity : AppCompatActivity(), PaymentResultListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        Checkout.preload(applicationContext)
        val co = Checkout()
        co.setKeyID("Set Your Api Key Here")

        val price = intent.getStringExtra("totalCost")

        try {
            val options = JSONObject()
            options.put("name","Ecommerce")
            options.put("description","Online Clothing Shop")
            //You can omit the image option to fetch the image from the dashboard
            options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
            options.put("theme.color", "#FF00E1");
            options.put("currency","INR");
            options.put("amount",(price!!.toInt()*100))//pass amount in currency subunits
            val prefill = JSONObject()
            prefill.put("email","kishanverma371997@gmail.com")
            prefill.put("contact","8130905701")

            options.put("prefill",prefill)
            co.open(this,options)
        }catch (e: Exception){
            Toast.makeText(this,"Error in payment: "+ e.message,Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
            Toast.makeText(this, "Payment success",  Toast.LENGTH_SHORT).show()
        uploadData()
    }

    private fun uploadData() {
        val id = intent.getStringArrayListExtra("productIds")
        for (currentId in id!!) {
            fetchData(currentId)
        }

    }

    private fun fetchData(productId: String?) {
        val dao = AppDataBase.getInstance(this).productDao()

        Firebase.firestore.collection("products")
            .document(productId!!).get().addOnSuccessListener {

                lifecycleScope.launch(Dispatchers.IO) {
                    dao.deleteProduct(Product(productId ))
                }

                saveData(it.getString("productName"),
                    it.getString("productSp"),
                    productId
                )
            }
    }

    private fun saveData(name : String?, sellingprice:String?, productId:String) {
        val preferences = this.getSharedPreferences("user", MODE_PRIVATE)
        val data = hashMapOf<String, Any>()
        data["name"] = name!!
        data["sellingprice"] = sellingprice!!
        data["productId"] = productId
        data["status"] = "Ordered"
        data["userId"] = preferences.getString("number", "")!!

        val firestore = Firebase.firestore.collection("allOrders")
        val key = firestore.document().id
        data["orderId"] = key

        firestore.document(key).set(data).addOnSuccessListener {
            Toast.makeText(this, "Order Placed", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
        }.addOnFailureListener {
            Toast.makeText(this, "Something went Wrong", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this, "Payment Error",  Toast.LENGTH_SHORT).show()
    }
}