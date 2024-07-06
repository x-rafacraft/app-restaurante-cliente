package com.example.appmenurestaurantcliente.network

import com.example.appmenurestaurantcliente.network.response.CategoriaResponse
import com.example.appmenurestaurantcliente.network.response.PlatilloResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WebService {

    @GET("/categorias")
    suspend fun obtenerCategorias(): Response<CategoriaResponse>

    @GET("/platillos/find/{nomCategoria}")
    suspend fun obtenerPlatillosCategoria(
        @Path("nomCategoria") nomCategoria: String
    ): Response<PlatilloResponse>

}