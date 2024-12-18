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
                    // Pantalla de bienvenida
                    composable("welcome") {
                        WelcomeScreen(
                            onStartClick = { navController.navigate("login") }
                        )
                    }
                    // Pantalla de inicio de sesión
                    composable("login") {
                        LoginScreen(
                            onNavigateToRegister = { navController.navigate("registration") }
                        )
                    }
                    // Pantalla de registro
                    composable("registration") {
                        RegistrationScreen(
                            onNavigateToLogin = { navController.navigate("login") }
                        )
                    }
                    // Pantalla de chat
                    composable("chat") {
                        ChatScreen()
                    }
                }
            }
        }
    }
}
