package com.kishan.ecommercapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.kishan.ecommercapp.databinding.ActivityLoginBinding
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var loader : AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpButton.setOnClickListener {
            openSignUp()
        }

        binding.loginButton.setOnClickListener {
            if (binding.etPhone.text!!.isEmpty()){
                Toast.makeText(this, "Please Fill that box", Toast.LENGTH_SHORT).show()
            }else {
                sendOtpToVerification(binding.etPhone.text.toString())
            }
        }
    }



    private fun sendOtpToVerification(userNumber: String) {
        loader = AlertDialog.Builder(this)
            .setTitle("Loading...")
            .setMessage("Please wait!!")
            .setCancelable(false)
            .create()
        loader.show()
        val option = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber("+91$userNumber")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(option)
    }

    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
        override fun onVerificationCompleted(p0: PhoneAuthCredential) {

        }

        override fun onVerificationFailed(p0: FirebaseException) {

        }

        override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
            loader.dismiss()
            val intent = Intent(this@LoginActivity, OTPActivity::class.java)
            intent.putExtra("verifyId", verificationId)
            intent.putExtra("number", binding.etPhone.text.toString())
            startActivity(intent)
        }
    }


    private fun openSignUp() {
        startActivity(Intent(this, SignUpActivity::class.java))
        finish()
    }
}