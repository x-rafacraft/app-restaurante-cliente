package com.example.appmenurestaurantcliente.models

import com.google.gson.annotations.SerializedName

data class Platillo(
    @SerializedName("nom_platillo")
    var nomPlatillo: String,
    @SerializedName("descripcion_platillo")
    var descPlatillo: String,
    @SerializedName("precio")
    var precio: String,
    @SerializedName("nom_categoria")
    var nomCategoria: String
)
