package com.jloppad.proyectoandroid

import NoticiasViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jloppad.proyectoandroid.ui.Navegacion
import com.jloppad.proyectoandroid.ui.theme.ProyectoAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Aplicar el tema de la app
            ProyectoAndroidTheme {
                // Crear ViewModel
                val viewModel: NoticiasViewModel = viewModel()

                // Configurar la navegación entre pantallas
                Navegacion(viewModel)
            }
        }
    }
}
