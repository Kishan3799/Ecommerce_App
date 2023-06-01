package com.kishan.ecommercapp.activity

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kishan.ecommercapp.databinding.ActivitySignUpBinding
import com.kishan.ecommercapp.model.UserModel

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            openLoginActivity()
        }

        binding.signUpButton.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        if(binding.etPhoneVerify.text!!.isEmpty() || binding.etUsernameVerify.text!!.isEmpty()){
            Toast.makeText(this, "Please fill the all fields", Toast.LENGTH_SHORT).show()
        }
        else{
            storeData()
        }
    }

    private fun storeData() {
        val loaderBuilder = AlertDialog.Builder(this)
            .setTitle("Loading...")
            .setMessage("Please wait!!")
            .setCancelable(false)
            .create()
        loaderBuilder.show()

        val preferences = this.getSharedPreferences("user", MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString("name", binding.etUsernameVerify.text.toString())
        editor.putString("number", binding.etPhoneVerify.text.toString())
        editor.apply()
//        val userData = hashMapOf<String, Any>()
//        userData["name"] = binding.etUsernameVerify.text.toString()
//        userData["number"] = binding.etPhoneVerify.text.toString()

        val data = UserModel(binding.etUsernameVerify.text.toString(),binding.etPhoneVerify.text.toString())


        Firebase.firestore.collection("users").document(binding.etPhoneVerify.text.toString())
            .set(data).addOnSuccessListener {
                Toast.makeText(this, "Registration Successfully", Toast.LENGTH_SHORT).show()
                loaderBuilder.dismiss()
                openLoginActivity()
            }.addOnFailureListener{
                loaderBuilder.dismiss()
                Toast.makeText(this, "Some Error here", Toast.LENGTH_SHORT).show()
            }
    }

    private fun openLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }



}