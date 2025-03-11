package com.jloppad.proyectoandroid.ui.navegacion

import com.jloppad.proyectoandroid.R
import com.jloppad.proyectoandroid.datos.modelo.TipoNoticia

sealed class Pantalla(val ruta: String, val titulo: String, val icono: Int, val tipoNoticia: TipoNoticia) {
    object Populares : Pantalla("populares", "Populares", R.drawable.stars, TipoNoticia.POPULARES)
    object Recientes : Pantalla("recientes", "Recientes", R.drawable.clock, TipoNoticia.RECIENTES)
    object Relevantes : Pantalla("relevantes", "Relevantes", R.drawable.search, TipoNoticia.RELEVANTES)
}

