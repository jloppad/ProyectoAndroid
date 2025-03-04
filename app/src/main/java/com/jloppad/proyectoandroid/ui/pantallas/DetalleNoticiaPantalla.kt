package com.jloppad.proyectoandroid.ui.pantallas

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import coil.compose.rememberAsyncImagePainter
import com.jloppad.proyectoandroid.datos.modelo.Articulo

@Composable
fun DetalleNoticiaPantalla(noticia: Articulo) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Imagen de la noticia
            if (!noticia.urlToImage.isNullOrEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(noticia.urlToImage),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Imagen no disponible",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Título
            Text(
                text = noticia.title,
                style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colors.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Fuente y Autor
            Text(
                text = "Fuente: ${noticia.source.name}",
                style = MaterialTheme.typography.body2,
                color = Color.Gray
            )
            noticia.author?.let {
                Text(
                    text = "Autor: $it",
                    style = MaterialTheme.typography.body2,
                    color = Color.Gray
                )
            }

            // Fecha de publicación
            Text(
                text = "Publicado el: ${noticia.publishedAt}",
                style = MaterialTheme.typography.body2,
                color = Color.Gray
            )

            Divider(modifier = Modifier.padding(vertical = 12.dp))

            // Descripción
            Text(
                text = noticia.description ?: "Sin descripción",
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Justify
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Botón "Leer más"
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(noticia.url))
                    context.startActivity(intent)
                },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
            ) {
                Text(text = "Leer más", color = Color.White)
            }
        }
    }
}
