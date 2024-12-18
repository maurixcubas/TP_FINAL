package com.elidacaceres.tpfinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.elidacaceres.tpfinal.ui.theme.TPfinalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TPfinalTheme {
                // Crear el controlador de navegación
                val navController = rememberNavController()

                // Configurar el NavHost
                NavHost(navController = navController, startDestination = "welcome") {
                    // Ruta para WelcomeScreen
                    composable("welcome") {
                        WelcomeScreen(
                            onStartClick = { navController.navigate("login") }
                        )
                    }
                    // Ruta para LoginScreen
                    composable("login") {
                        LoginScreen(
                            onNavigateToRegister = { navController.navigate("registration") },
                            onNavigateToChat = { navController.navigate("chat_options") }
                        )
                    }
                    // Ruta para RegistrationScreen
                    composable("registration") {
                        RegistrationScreen(
                            onNavigateToLogin = { navController.navigate("login") }
                        )
                    }
                    // Ruta para ChatOptionsScreen
                    composable("chat_options") {
                        ChatOptionsScreen(
                            onNavigateToCurrentChat = { navController.navigate("chat_screen") },
                            onNavigateToPreviousChats = { navController.navigate("previous_chats") }
                        )
                    }
                    // Ruta para PreviousChatsScreen (comentado si no lo usas)
                    // composable("previous_chats") {
                    //    PreviousChatsScreen { selectedChat -> println("Chat seleccionado: $selectedChat") }
                    // }
                    // Ruta para ChatScreen
                    composable("chat_screen") {
                        ChatScreen() // Asegúrate de tener la pantalla de chat definida aquí
                    }
                }
            }
        }
    }
}
