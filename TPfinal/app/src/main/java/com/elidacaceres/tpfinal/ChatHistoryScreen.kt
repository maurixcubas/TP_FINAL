package com.elidacaceres.tpfinal

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatHistoryScreen(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    var threads by remember { mutableStateOf<List<ThreadsResponse>>(emptyList()) }
    var isDeleting by remember { mutableStateOf(false) } // Estado para mostrar carga

    // Cargar historial de hilos al iniciar
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            threads = loadThreads()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Historial de Chats", style = MaterialTheme.typography.headlineMedium) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("chat_options") }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn {
                items(threads) { thread ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                // Navegar al thread seleccionado
                                navController.navigate("chat_screen/${thread.thread_id}")
                            },
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("Chat iniciado: ${thread.created_at}")
                            }
                            // Botón de Eliminar
                            IconButton(
                                onClick = {
                                    coroutineScope.launch {
                                        isDeleting = true
                                        val success = deleteThread(thread.thread_id)
                                        isDeleting = false
                                        if (success) {
                                            threads = loadThreads() // Recargar los threads
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "Eliminar",
                                    tint = MaterialTheme.colorScheme.error // Color rojo
                                )
                            }
                        }
                    }
                }
            }
            if (isDeleting) {
                CircularProgressIndicator(modifier = Modifier.padding(top = 8.dp))
            }
        }
    }
}

/**
 * Función auxiliar para cargar los threads
 */
suspend fun loadThreads(): List<ThreadsResponse> {
    return try {
        val response = RetrofitInstance.api.getThreads()
        if (response.isSuccessful) {
            response.body() ?: emptyList()
        } else {
            println("Error al cargar threads: Código ${response.code()}, Mensaje ${response.message()}")
            emptyList()
        }
    } catch (e: Exception) {
        println("Error al cargar threads: ${e.message}")
        emptyList()
    }
}

/**
 * Función auxiliar para eliminar un thread
 */
suspend fun deleteThread(threadId: String): Boolean {
    return try {
        val response = RetrofitInstance.api.deleteThread(threadId)
        if (response.isSuccessful) {
            println("Thread eliminado correctamente.")
            true
        } else {
            println("Error al eliminar thread: Código ${response.code()}, Mensaje ${response.message()}")
            false
        }
    } catch (e: Exception) {
        println("Error al eliminar thread: ${e.message}")
        false
    }
}
