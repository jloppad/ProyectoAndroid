package com.jloppad.proyectoandroid.ui.navegacion

import NoticiasViewModel
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.jloppad.proyectoandroid.ui.pantallas.ListaNoticiasPopularesPantalla
import com.jloppad.proyectoandroid.ui.pantallas.DetalleNoticiaPantalla
import com.jloppad.proyectoandroid.ui.pantallas.ListaNoticiasRecientesPantalla
import com.jloppad.proyectoandroid.ui.pantallas.ListaNoticiasRelevantesPantalla

@Composable
fun Navegacion(viewModel: NoticiasViewModel) {
    val navController = rememberNavController()
    val listaNoticiasPopulares by viewModel.listaNoticiasPopulares.collectAsState()
    val listaNoticiasNuevas by viewModel.listaNoticiasRecientes.collectAsState()
    val listaNoticiasRelevantes by viewModel.listaNoticiasRelevantes.collectAsState()
    val cargando by viewModel.cargando.collectAsState()
    val error by viewModel.error.collectAsState()
    val busqueda by viewModel.busqueda.collectAsState()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { paddingValues ->
        NavHost(
            navController,
            startDestination = Pantalla.Recientes.ruta,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Pantalla.Populares.ruta) {
                ListaNoticiasPopularesPantalla(
                    noticias = listaNoticiasPopulares,
                    cargando = cargando,
                    error = error,
                    onNoticiaClick = { noticia ->
                        navController.navigate("detalleNoticia/${Uri.encode(noticia.title)}")
                    },
                    onBusquedaChange = { nuevaBusqueda ->
                        viewModel.actualizarBusqueda(nuevaBusqueda)
                    },
                    busqueda = busqueda
                )
            }

            composable(Pantalla.Recientes.ruta) {
                ListaNoticiasRecientesPantalla(
                    noticias = listaNoticiasNuevas,
                    cargando = cargando,
                    error = error,
                    onNoticiaClick = { noticia ->
                        navController.navigate("detalleNoticia/${Uri.encode(noticia.title)}")
                    },
                    onBusquedaChange = { nuevaBusqueda ->
                        viewModel.actualizarBusqueda(nuevaBusqueda)
                    },
                    busqueda = busqueda
                )
            }

            composable(Pantalla.Relevantes.ruta) {
                ListaNoticiasRelevantesPantalla(
                    noticias = listaNoticiasRelevantes,
                    cargando = cargando,
                    error = error,
                    onNoticiaClick = { noticia ->
                        navController.navigate("detalleNoticia/${Uri.encode(noticia.title)}")
                    },
                    onBusquedaChange = { nuevaBusqueda ->
                        viewModel.actualizarBusqueda(nuevaBusqueda)
                    },
                    busqueda = busqueda
                )
            }

            composable(
                route = "detalleNoticia/{title}",
                arguments = listOf(navArgument("title") { type = NavType.StringType })
            ) { backStackEntry ->
                val title = backStackEntry.arguments?.getString("title") ?: ""

                val noticiaSeleccionada = listaNoticiasPopulares.find { it.title == title }
                    ?: listaNoticiasNuevas.find { it.title == title }
                    ?: listaNoticiasRelevantes.find { it.title == title }


                if (noticiaSeleccionada != null) {
                    DetalleNoticiaPantalla(noticiaSeleccionada)
                } else {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "No se encontró la noticia", color = Color.Red)
                    }
                }
            }
        }
    }
}
