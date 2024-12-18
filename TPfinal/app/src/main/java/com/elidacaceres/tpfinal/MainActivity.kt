package com.elidacaceres.tpfinal

import ChatScreen
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
                        HomeOptions(
                            onNavigateToCurrentChat = { navController.navigate("chat_screen") },
                            onNavigateToPreviousChats = { navController.navigate("chat_history") }
                        )
                    }
                    // Ruta para PreviousChatsScreen (comentado si no lo usas)
                    // composable("previous_chats") {
                    //    PreviousChatsScreen { selectedChat -> println("Chat seleccionado: $selectedChat") }
                    // }

                    composable("chat_history") {
                        ChatHistoryScreen(navController)
                    }
                    composable("chat_screen") { // Caso nuevo chat
                        ChatScreen(navController, null)
                    }
                    composable("chat_screen/{threadId}") { backStackEntry -> // Caso chat existente
                        val threadId = backStackEntry.arguments?.getString("threadId")
                        ChatScreen(navController, threadId)
                    }
                }
            }
        }
    }
}
