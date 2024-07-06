package com.example.appmenurestaurantcliente.network.response

import com.example.appmenurestaurantcliente.models.Platillo
import com.google.gson.annotations.SerializedName

data class PlatilloResponse(
    @SerializedName("codigo")
    val codigo: String,
    @SerializedName("mensaje")
    val mensaje: String,
    @SerializedName("datos")
    val datos: ArrayList<Platillo>
)
