package com.jloppad.proyectoandroid.datos.modelo

import com.google.gson.annotations.SerializedName

data class RespuestaNoticias(
    @SerializedName("articles") val articulos: List<Articulo>?
)