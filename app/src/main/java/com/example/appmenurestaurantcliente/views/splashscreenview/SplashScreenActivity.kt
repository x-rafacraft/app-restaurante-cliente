package com.example.appmenurestaurantcliente.views.splashscreenview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.example.appmenurestaurantcliente.R
import com.example.appmenurestaurantcliente.databinding.ActivitySplashScreenBinding
import com.example.appmenurestaurantcliente.utils.Constantes
import com.example.appmenurestaurantcliente.views.categoriasscreenview.CategoriasActivity

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        Glide
            .with(this)
            .load(R.drawable.restautant)
            .into(binding.gifLogo)

        cambiarActivity()

    }

    private fun cambiarActivity(){
        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(this, CategoriasActivity::class.java)
            startActivity(intent)
            finish()
        }, Constantes.DURACION_SPLASH_SCREEN )

    }

}