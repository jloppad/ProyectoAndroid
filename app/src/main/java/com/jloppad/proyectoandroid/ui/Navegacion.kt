package com.jloppad.proyectoandroid.ui

import NoticiasViewModel
import androidx.compose.runtime.*
import androidx.navigation.compose.*
import com.jloppad.proyectoandroid.ui.pantallas.ListaNoticiasPantalla
import com.jloppad.proyectoandroid.ui.pantallas.DetalleNoticiaPantalla

@Composable
fun Navegacion(viewModel: NoticiasViewModel) {
    val navController = rememberNavController()
    val listaNoticias by viewModel.listaNoticias.collectAsState()
    val cargando by viewModel.cargando.collectAsState()
    val error by viewModel.error.collectAsState()
    val busqueda by viewModel.busqueda.collectAsState()

    NavHost(navController, startDestination = "listaNoticias") {
        composable("listaNoticias") {
            ListaNoticiasPantalla(
                noticias = listaNoticias,
                cargando = cargando,
                error = error,
                onNoticiaClick = { noticia ->
                    navController.navigate("detalleNoticia/${noticia.title}")
                },
                onBusquedaChange = { nuevaBusqueda ->
                    viewModel.actualizarBusqueda(nuevaBusqueda)
                },
                busqueda = busqueda
            )
        }
        composable("detalleNoticia/{title}") { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val noticiaSeleccionada = listaNoticias.find { it.title == title }

            if (noticiaSeleccionada != null) {
                DetalleNoticiaPantalla(noticiaSeleccionada)
            }
        }
    }
}
