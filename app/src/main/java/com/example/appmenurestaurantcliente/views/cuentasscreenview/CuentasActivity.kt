package com.example.appmenurestaurantcliente.views.cuentasscreenview

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.appmenurestaurantcliente.R
import com.example.appmenurestaurantcliente.databinding.ActivityCuentasBinding
import com.example.appmenurestaurantcliente.utils.Utils
import es.dmoral.toasty.Toasty

class CuentasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCuentasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityCuentasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "CUENTA"
        window.statusBarColor = resources.getColor(R.color.mar_infinito)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.valle_dorado)))

        var activity = intent.getStringExtra("activity")
        Utils(this, binding.tvCuentaTotal).validarCuentaTotal()

        obtenerPlatillosCuenta()

        binding.tvCuentaTotal.setOnClickListener{
            Toasty.success(this@CuentasActivity, "Se pagó la cuenta", Toasty.LENGTH_SHORT, true).show()
            Utils(this).limpiarCuenta()

            val intent = Intent()
            when(activity){
                "cat" -> setResult(RESULT_OK, intent)
                "plat" -> setResult(RESULT_OK, intent)
            }
            finish()
        }
    }

    private fun obtenerPlatillosCuenta(){
        var listaSharedPreferences =  Utils(this).obtenerPlatillos().split(".")
        mostrarCuenta(listaSharedPreferences)
    }

    private fun mostrarCuenta(lista: List<String>){
        var posicion = 0
        while (posicion < lista.size){
            var fila = TableRow(this)

            var tvPlatillo = TextView(this)
            tvPlatillo.setPadding(12, 12, 12, 12)
            tvPlatillo.setTextSize(20f)
            tvPlatillo.setText(lista[posicion])

            fila.addView(tvPlatillo)

            posicion++

            binding.tlCuenta.addView(fila)
        }
    }
}