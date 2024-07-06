package com.example.appmenurestaurantcliente.views.platillosscreenview

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmenurestaurantcliente.R
import com.example.appmenurestaurantcliente.databinding.ActivityPlatillosBinding
import com.example.appmenurestaurantcliente.models.Platillo
import com.example.appmenurestaurantcliente.network.RetrofitClient
import com.example.appmenurestaurantcliente.utils.Utils
import com.example.appmenurestaurantcliente.views.cuentasscreenview.CuentasActivity
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlatillosActivity : AppCompatActivity(), AdaptadorPlatillos.OnItemClicked {

    private lateinit var binding : ActivityPlatillosBinding
    var listaPlatillos = ArrayList<Platillo>()
    lateinit var adaptadorPlatillos: AdaptadorPlatillos

    var startActivityIntent = registerForActivityResult<Intent, androidx.activity.result.ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ){
        //Toast.makeText(this, "REGRESAR A CATEGORIAS", Toast.LENGTH_SHORT).show()
        Utils(this@PlatillosActivity, binding.tvCuentaTotal).validarCuentaTotal()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityPlatillosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nomCategoria =  intent.extras?.getString("nomCategoria")

       supportActionBar?.title = "MENÚ ${nomCategoria?.uppercase()}"
        window.statusBarColor =  resources.getColor(R.color.mar_infinito)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.valle_dorado)))

        obtenerPlatillos(nomCategoria!!.lowercase())

        Utils(this@PlatillosActivity, binding.tvCuentaTotal).validarCuentaTotal()

        binding.tvCuentaTotal.setOnClickListener{
            var intent = Intent(this, CuentasActivity::class.java)
            intent.putExtra("activity", "plat")
            startActivityIntent.launch(intent)
        }
    }

    private fun obtenerPlatillos(nomCategoria: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = RetrofitClient.webService.obtenerPlatillosCategoria(nomCategoria)
            if (call.isSuccessful){
                if(call.body()!!.codigo == "200"){
                    listaPlatillos = call.body()!!.datos
                    setupRecyclerView()
                } else {
                    Toasty.error(this@PlatillosActivity, call.body()!!.mensaje, Toasty.LENGTH_SHORT, true).show()
                }
            } else {
                Toasty.error(this@PlatillosActivity, "ERROR EN LA CONSULTA", Toasty.LENGTH_SHORT, true).show()
            }
        }
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvPlatillos.layoutManager = layoutManager
        adaptadorPlatillos = AdaptadorPlatillos(listaPlatillos, this)
        binding.rvPlatillos.adapter = adaptadorPlatillos

    }

    override fun agregarPlatilloCuenta(nomPlatillo: String, precio: String) {
        Utils(this@PlatillosActivity, binding.tvCuentaTotal).agregarPlatilloCuenta(nomPlatillo, precio.toDouble())
        Toasty.success(this@PlatillosActivity, "Se agregó el $nomPlatillo a la cuenta", Toasty.LENGTH_SHORT, true).show()
    }

}