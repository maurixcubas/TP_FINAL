package com.elidacaceres.tpfinal

import LoginViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.livedata.observeAsState
import com.elidacaceres.tpfinal.ui.theme.TPfinalTheme

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = LoginViewModel(),
    onNavigateToRegister: () -> Unit // Callback para navegación
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Observa el estado del inicio de sesión desde el ViewModel
    val loginResult by viewModel.loginResult.observeAsState(initial = false)
    val errorMessage by viewModel.errorMessage.observeAsState(initial = "")

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
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Iniciar Sesión")
                }
                Spacer(modifier = Modifier.height(8.dp))
                // Botón de navegación
                TextButton(onClick = onNavigateToRegister) {
                    Text("¿No tienes cuenta? Regístrate aquí")
                }

                Spacer(modifier = Modifier.height(16.dp))
                when {
                    loginResult -> Text("Inicio de sesión exitoso", style = MaterialTheme.typography.bodyMedium)
                    errorMessage.isNotEmpty() -> Text(
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
        LoginScreen(onNavigateToRegister = { /* Acción de navegación aquí */ })
    }
}
