package com.jloppad.proyectoandroid.repositorio

import com.jloppad.proyectoandroid.datos.modelo.RespuestaNoticias
import com.jloppad.proyectoandroid.datos.red.RetrofitInstancia

class NoticiasRepositorio {
    suspend fun obtenerNoticias(claveApi: String, nuevaBusqueda: String): RespuestaNoticias {
        return RetrofitInstancia.api.obtenerNoticias(claveApi = claveApi, busqueda = nuevaBusqueda)
    }
}
