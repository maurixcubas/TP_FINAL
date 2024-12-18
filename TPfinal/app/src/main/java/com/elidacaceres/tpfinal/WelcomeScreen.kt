package com.elidacaceres.tpfinal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WelcomeScreen(onStartClick: () -> Unit) {
    // Diseñamos la pantalla de bienvenida
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            // Ícono de la aplicación
            Image(
                painter = painterResource(id = R.drawable.logo_2), // Asegúrate de que el nombre del archivo sea correcto
                contentDescription = "Logo de la App",
                modifier = Modifier
                    .size(290.dp)
                    .padding(bottom = 32.dp)
            )
            // Título de bienvenida
            Text(
                text = "¡Bienvenido a UAABoot!",
                fontSize = 24.sp,
                color = Color(0xFF800020), // Color bordo
                fontWeight = FontWeight.Bold, // Negrita
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )
            // Botón de inicio
            Button(
                onClick = { onStartClick() }, // Al hacer clic, ejecuta la acción pasada
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFB04A5C), // Bordó claro
                    contentColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Iniciar",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}