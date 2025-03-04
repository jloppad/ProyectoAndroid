package com.jloppad.proyectoandroid.datos.red

import com.jloppad.proyectoandroid.datos.modelo.RespuestaNoticias
import retrofit2.http.GET
import retrofit2.http.Query

interface NoticiasApiServicio {
    @GET("v2/everything")
    suspend fun obtenerNoticias(
        @Query("q") busqueda: String = "Apple",
        @Query("sortBy") ordenar: String = "popularity",
        @Query("apiKey") claveApi: String
    ): RespuestaNoticias
}
