package com.kishan.ecommercapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.kishan.ecommercapp.MainActivity
import com.kishan.ecommercapp.databinding.ActivityOtpactivityBinding

class OTPActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOtpactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.continueButton.setOnClickListener {
            if (binding.etOtpVerify.text!!.isEmpty()){
                Toast.makeText(this, "Please send otp", Toast.LENGTH_SHORT).show()
            }else {
                verifyUser(binding.etOtpVerify.text.toString())
            }
        }
    }

    private fun verifyUser(otp: String) {
        val  credential = PhoneAuthProvider.getCredential(intent.getStringExtra("verifyId")!!, otp)
        signInWithPhoneCredential(credential)
    }

    private fun signInWithPhoneCredential(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener {task ->
                if(task.isSuccessful){


                    val preferences = this.getSharedPreferences("user", MODE_PRIVATE)
                    val editor = preferences.edit()
                    editor.putString("number", intent.getStringExtra("number"))
                    editor.apply()
                   startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }else{
                    if(task.exception is FirebaseAuthInvalidCredentialsException){
                    Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show()}
                }
            }
    }
}