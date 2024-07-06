package com.example.appmenurestaurantcliente.views.categoriasscreenview

import android.app.Instrumentation
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appmenurestaurantcliente.R
import com.example.appmenurestaurantcliente.databinding.ActivityCategoriasBinding
import com.example.appmenurestaurantcliente.models.Categoria
import com.example.appmenurestaurantcliente.network.RetrofitClient
import com.example.appmenurestaurantcliente.utils.Utils
import com.example.appmenurestaurantcliente.views.cuentasscreenview.CuentasActivity
import com.example.appmenurestaurantcliente.views.platillosscreenview.PlatillosActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import es.dmoral.toasty.Toasty

class CategoriasActivity : AppCompatActivity(), AdaptadorCategorias.OnItemClicked {

    private lateinit var binding: ActivityCategoriasBinding
    var listaCategorias = ArrayList<Categoria>()
    lateinit var adaptadorCategoria: AdaptadorCategorias

    var startActivityIntent = registerForActivityResult<Intent, androidx.activity.result.ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ){
        //Toast.makeText(this, "REGRESAR A CATEGORIAS", Toast.LENGTH_SHORT).show()
        Utils(this@CategoriasActivity, binding.tvCuentaTotal).validarCuentaTotal()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityCategoriasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.title = "RESTAURANT APP"
        window.statusBarColor =  resources.getColor(R.color.mar_infinito)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.valle_dorado)))

        obtenerCategorias()

        Utils(this@CategoriasActivity, binding.tvCuentaTotal).validarCuentaTotal()

        binding.rbStar.setOnRatingBarChangeListener{ ratingBar, fl, b ->
            Toasty.info(this@CategoriasActivity,
                "GRACIAS POR TU CALIFICACIÓN ${fl} DE ESTRELLAS :)",
                Toasty.LENGTH_SHORT, true).show()
        }

        binding.tvCuentaTotal.setOnClickListener{
            var intent = Intent(this, CuentasActivity::class.java)
            intent.putExtra("activity", "cat")
            startActivityIntent.launch(intent)
        }
    }

    private fun obtenerCategorias() {

        CoroutineScope(Dispatchers.IO).launch {
            val call = RetrofitClient.webService.obtenerCategorias()
            runOnUiThread {
                if (call.isSuccessful) {
                    if (call.body()!!.codigo == "200") {
                        listaCategorias = call.body()!!.datos
                        setupRecyclerView()
                    } else {
                        Toasty.error(this@CategoriasActivity, call.body()!!.mensaje, Toasty.LENGTH_SHORT).show()
                    }
                } else {
                    Toasty.error(this@CategoriasActivity, "ERROR EN LA CONSULTA", Toasty.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun setupRecyclerView(){
        val layoutManager =  GridLayoutManager(this, 2)
        binding.rvCategorias.layoutManager = layoutManager
        adaptadorCategoria =  AdaptadorCategorias(this, listaCategorias, this)
        binding.rvCategorias.adapter = adaptadorCategoria
    }

    override fun verPlatillosCategoria(nomCategoria: String) {
        val intent = Intent(this, PlatillosActivity::class.java)
        intent.putExtra("nomCategoria", nomCategoria)
        startActivityIntent.launch(intent)
    }

}