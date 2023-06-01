package com.kishan.ecommercapp.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kishan.ecommercapp.R
import com.kishan.ecommercapp.databinding.ActivityAddressBinding

class AddressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddressBinding
    private lateinit var preferences : SharedPreferences
    private lateinit var totalCost:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferences = this.getSharedPreferences("user", MODE_PRIVATE)
        totalCost = intent.getStringExtra("totalCost")!!
        loadUserInfo()

        binding.proceedBtn.setOnClickListener {
            validateData(
                binding.etUserName.text.toString(),
                binding.etNumber.text.toString(),
                binding.etVillage.text.toString(),
                binding.etCity.text.toString(),
                binding.etState.text.toString(),
                binding.etPinCode.text.toString(),
            )
        }
    }

    private fun validateData(name: String, number: String, village: String, city: String, state: String, pinCode: String) {

        if(name.isEmpty() || number.isEmpty() || village.isEmpty() || city.isEmpty() || state.isEmpty() || pinCode.isEmpty()){
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        } else {
            storeData(village, city, state, pinCode)
        }

    }

    private fun storeData(village: String, city: String, state: String, pinCode: String) {
        val map = hashMapOf<String, Any>()
        map["village"] = village
        map["city"] = city
        map["state"] = state
        map["pinCode"] = pinCode

        Firebase.firestore.collection("users")
            .document(preferences.getString("number", "")!!)
            .update(map).addOnSuccessListener {
                val b = Bundle()
                b.putStringArrayList("productIds", intent.getStringArrayListExtra("productIds"))
                b.putString("totalCost", totalCost)
                intent.putExtras(b)
                val intent = Intent(this, CheckoutActivity::class.java)
                intent.putExtras(b)
                startActivity(intent)
            }.addOnFailureListener{
                Toast.makeText(this, "Something went Wrong", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadUserInfo() {
        Firebase.firestore.collection("users")
            .document(preferences.getString("number", "")!!)
            .get().addOnSuccessListener {
                binding.etUserName.setText(it.getString("userName"))
                binding.etNumber.setText(it.getString("userPhoneNumber"))
                binding.etVillage.setText(it.getString("village"))
                binding.etCity.setText(it.getString("city"))
                binding.etState.setText(it.getString("state"))
                binding.etPinCode.setText(it.getString("pinCode"))
            }.addOnFailureListener {
                Toast.makeText(this, "Something went Wrong", Toast.LENGTH_SHORT).show()
            }
    }
}