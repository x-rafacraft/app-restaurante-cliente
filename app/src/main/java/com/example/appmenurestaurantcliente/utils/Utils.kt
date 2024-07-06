package com.example.appmenurestaurantcliente.utils

import android.content.Context
import android.content.SharedPreferences
import android.widget.TextView

class Utils {

    var context: Context
    var sharedPreferences: SharedPreferences
    lateinit var tvCuentaTotal: TextView

    constructor(context: Context) {
        this.context =  context
        this.sharedPreferences = context.getSharedPreferences(Constantes.SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }

    constructor(context: Context, tvCuentaTotal: TextView){
        this.context = context
        this.tvCuentaTotal = tvCuentaTotal
        this.sharedPreferences = context.getSharedPreferences(Constantes.SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }

    fun agregarPlatilloCuenta (nomPlatillo: String, precio: Double){
        var platillos = obtenerPlatillos()
        if (platillos.equals("")){
            platillos = "$nomPlatillo-$precio"
        } else {
            platillos = "$platillos.$nomPlatillo-$precio"
        }

        var editor = sharedPreferences.edit()
        editor.putString("platillos", platillos)

        var totalGuardado = obtenerTotalCuenta()
        var total = totalGuardado!!.toDouble() + precio
        editor.putString("total", total.toString())

        editor.commit()

        validarCuentaTotal()
    }

    fun obtenerPlatillos(): String{
        var platillos =  sharedPreferences.getString("platillos","")
        return platillos.toString()
    }

    fun obtenerTotalCuenta(): String{
        var spTotal = sharedPreferences.getString("total", "0.0")
        return spTotal!!
    }

    fun validarCuentaTotal(){
        if(obtenerTotalCuenta().isNullOrEmpty()){
            this.tvCuentaTotal.text = "Pagar cuenta de: S/ ${obtenerTotalCuenta()} SOLES"
        } else {
            this.tvCuentaTotal.text = "Pagar cuenta de: S/ ${obtenerTotalCuenta()} SOLES"
        }
    }

    fun limpiarCuenta(){
        var editor = sharedPreferences.edit()
        editor.remove("platillos")
        editor.remove("total")
        editor.commit()
    }
}