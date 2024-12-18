package com.elidacaceres.tpfinal

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp

// Pantalla con opciones de navegación
@Composable
fun Hompage(
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
        Text(
            text = "Selecciona una opción",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = onNavigateToCurrentChat, // Navega a la pantalla de nuevo chat
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Text("Nuevo Chat")
        }

        Button(
            onClick = onNavigateToPreviousChats, // Navega a la pantalla de chats anteriores
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ver Chats Anteriores")
        }
    }
}
