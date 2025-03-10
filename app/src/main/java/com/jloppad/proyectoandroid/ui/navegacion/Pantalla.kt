package com.jloppad.proyectoandroid.ui.navegacion

import com.jloppad.proyectoandroid.R

sealed class Pantalla(val ruta: String, val titulo: String, val icono: Int) {
    object Populares : Pantalla("populates", "Populares", R.drawable.stars)
    object Recientes : Pantalla("recientes", "Recientes", R.drawable.clock)
    object Relevantes : Pantalla("relevantes", "Relevantes", R.drawable.search)
}
