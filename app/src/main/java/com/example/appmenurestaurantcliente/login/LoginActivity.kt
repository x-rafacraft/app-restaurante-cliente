package com.example.appmenurestaurantcliente

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.appmenurestaurantcliente.databinding.ActivityLoginBinding
import com.example.appmenurestaurantcliente.views.splashscreenview.SplashScreenActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.tvRegistrarme.setOnClickListener{
            val intent = Intent(this , RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnIngresar.setOnClickListener{
            val email = binding.etEmail.text.toString()
            val pass = binding.etPassword.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email , pass).addOnCompleteListener{
                    if (it.isSuccessful){
                        val intent = Intent(this , SplashScreenActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this , it.exception.toString() , Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this , "Las contraseñas no coinciden" , Toast.LENGTH_SHORT).show()
            }
        }
    }
//    override fun onStart() {
//        super.onStart()
//
//        if(firebaseAuth.currentUser != null){
//            val intent = Intent(this , MainActivity::class.java)
//            startActivity(intent)
//        }
//    }
}