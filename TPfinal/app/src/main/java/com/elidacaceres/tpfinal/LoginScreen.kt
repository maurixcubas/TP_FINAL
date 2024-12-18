package com.elidacaceres.tpfinal

import LoginViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.elidacaceres.tpfinal.ui.theme.TPfinalTheme

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = LoginViewModel(),
    onNavigateToRegister: () -> Unit,
    onNavigateToChat: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Observa el estado del inicio de sesión desde el ViewModel
    val loginResult by viewModel.loginResult.observeAsState(initial = false)
    val errorMessage by viewModel.errorMessage.observeAsState(initial = "")

    // Detecta si el login fue exitoso y navega al ChatScreen
    LaunchedEffect(loginResult) {
        if (loginResult) {
            onNavigateToChat()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_2), // Asegúrate que el nombre coincide con el recurso
                    contentDescription = "Logo de la app",
                    modifier = Modifier
                        .size(120.dp) // Ajusta el tamaño del logo
                        .padding(bottom = 16.dp),
                    contentScale = ContentScale.Fit
                )
                Text(text = "Inicio de Sesión", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo electrónico") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        viewModel.login(email, password)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF800000), // Color de fondo del botón
                        contentColor = Color.White  // Color del texto
                    )
                ) {
                    Text("Iniciar Sesión")
                }
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(onClick = onNavigateToRegister) {
                    Text("¿No tienes cuenta? Regístrate aquí")
                }
                Spacer(modifier = Modifier.height(16.dp))
                if (errorMessage.isNotEmpty()) {
                    Text(
                        "Error: $errorMessage",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    TPfinalTheme {
        LoginScreen(
            onNavigateToRegister = {},
            onNavigateToChat = {}
        )
    }
}
