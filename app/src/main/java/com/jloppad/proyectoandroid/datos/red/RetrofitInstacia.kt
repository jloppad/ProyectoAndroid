package com.jloppad.proyectoandroid.datos.red

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstancia {
    private const val BASE_URL = "https://newsapi.org/"

    val api: NoticiasApiServicio by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NoticiasApiServicio::class.java)
    }
}