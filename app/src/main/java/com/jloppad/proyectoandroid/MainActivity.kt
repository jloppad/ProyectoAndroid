package com.jloppad.proyectoandroid

import NoticiasViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jloppad.proyectoandroid.ui.navegacion.Navegacion
import com.jloppad.proyectoandroid.ui.tema.ProyectoAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectoAndroidTheme {
                val viewModel: NoticiasViewModel = viewModel()
                Navegacion(viewModel)
            }
        }
    }
}

