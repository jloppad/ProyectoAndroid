package com.jloppad.proyectoandroid.ui.pantallas

import androidx.compose.foundation.clickable
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jloppad.proyectoandroid.datos.modelo.Articulo
import com.jloppad.proyectoandroid.ui.theme.Blue500
import com.jloppad.proyectoandroid.ui.theme.Blue700

@Composable
fun ListaNoticiasRecientesPantalla(
    noticias: List<Articulo>,
    cargando: Boolean,
    error: String?,
    onNoticiaClick: (Articulo) -> Unit,
    onBusquedaChange: (String) -> Unit,
    busqueda: String
) {
    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = busqueda,
            onValueChange = { nuevaBusqueda -> onBusquedaChange(nuevaBusqueda) },
            label = { Text("Buscar noticias recientes") },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Buscar") },
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Blue500,
                unfocusedBorderColor = Color.Gray,
                cursorColor = Blue700
            ),
            modifier = Modifier.fillMaxWidth()
        )

        error?.let {
            Text(text = it, color = Color.Red)
        }

        when {
            cargando -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            error != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = error, color = Color.Red)
                }
            }
            noticias.isEmpty() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "No hay noticias recientes disponibles", color = Color.Gray)
                }
            }
            else -> {
                LazyColumn {
                    items(noticias) { noticia ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable { onNoticiaClick(noticia) },
                            elevation = 4.dp
                        ) {
                            Column {
                                AsyncImage(
                                    model = noticia.urlToImage,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp),
                                    contentScale = ContentScale.Crop
                                )
                                Text(
                                    text = noticia.title,
                                    modifier = Modifier.padding(8.dp),
                                    color = Color.Black,
                                    fontWeight = FontWeight.Normal,
                                    style = MaterialTheme.typography.h6
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


