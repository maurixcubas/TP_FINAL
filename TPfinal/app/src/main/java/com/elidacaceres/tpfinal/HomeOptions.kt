package com.elidacaceres.tpfinal

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeOptions(
    onNavigateToCurrentChat: () -> Unit, // Navegar al nuevo chat
    onNavigateToPreviousChats: () -> Unit // Navegar a chats anteriores
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo en la parte superior
        println("Cargando imagen: logo_2.png")

        Image(
            painter = painterResource(id = R.drawable.logo_2), // Asegúrate que el nombre coincide con el recurso
            contentDescription = "Logo de la app",
            modifier = Modifier
                .size(120.dp) // Ajusta el tamaño del logo
                .padding(bottom = 16.dp),
            contentScale = ContentScale.Fit
        )

        // Texto de selección de opción
        Text(
            text = "Selecciona una opción",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Botón para iniciar un nuevo chat
        Button(
            onClick = onNavigateToCurrentChat, // Navega a la pantalla de nuevo chat
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF800000), // Color de fondo del botón
                contentColor = Color.White  // Color del texto
            )

        ) {
            Text("Nuevo Chat")
        }

        // Botón para ver chats anteriores
        Button(
            onClick = onNavigateToPreviousChats, // Navega a la pantalla de chats anteriores
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF800000), // Color de fondo del botón
                contentColor = Color.White  // Color del texto
            )
        ) {
            Text("Ver Chats Anteriores")
        }
    }
}
