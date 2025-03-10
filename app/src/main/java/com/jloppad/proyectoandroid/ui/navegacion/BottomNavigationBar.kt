package com.jloppad.proyectoandroid.ui.navegacion

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(Pantalla.Populares, Pantalla.Recientes, Pantalla.Relevantes)
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    BottomNavigation {
        items.forEach { pantalla ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = pantalla.icono), contentDescription = pantalla.titulo) },
                label = { Text(text = pantalla.titulo) },
                selected = currentRoute == pantalla.ruta,
                onClick = {
                    navController.navigate(pantalla.ruta) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
