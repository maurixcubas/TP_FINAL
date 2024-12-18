package com.elidacaceres.tpfinal

import RegisterViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RegistrationScreen(onNavigateToLogin: () -> Unit, viewModel: RegisterViewModel = viewModel()) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val registrationState by viewModel.registrationState.observeAsState(RegistrationState.Idle)

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
                RegistrationTextField(
                    value = firstName,
                    label = "Nombre",
                    onValueChange = { firstName = it }
                )
                Spacer(modifier = Modifier.height(8.dp))

                RegistrationTextField(
                    value = lastName,
                    label = "Apellido",
                    onValueChange = { lastName = it }
                )
                Spacer(modifier = Modifier.height(8.dp))

                RegistrationTextField(
                    value = email,
                    label = "Correo Electrónico",
                    onValueChange = { email = it }
                )
                Spacer(modifier = Modifier.height(8.dp))

                RegistrationTextField(
                    value = phoneNumber,
                    label = "Número Celular",
                    onValueChange = { phoneNumber = it }
                )
                Spacer(modifier = Modifier.height(8.dp))

                RegistrationTextField(
                    value = password,
                    label = "Contraseña",
                    isPassword = true,
                    onValueChange = { password = it }
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.registerUser(
                            firstName,
                            lastName,
                            email,
                            phoneNumber,
                            password
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Registrar")
                }

                Spacer(modifier = Modifier.height(8.dp))
                TextButton(onClick = onNavigateToLogin) {
                    Text("¿Ya tienes cuenta? Inicia sesión aquí")
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Mostrar estados de registro
                when (registrationState) {
                    is RegistrationState.Loading -> CircularProgressIndicator()
                    is RegistrationState.Success -> {
                        Text(
                            text = (registrationState as RegistrationState.Success).message,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    is RegistrationState.Error -> {
                        Text(
                            text = (registrationState as RegistrationState.Error).errorMessage,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    else -> {}
                }
            }
        }
    )
}

@Composable
fun RegistrationTextField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false  // Corregido a Boolean
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = Modifier.fillMaxWidth()
    )
}