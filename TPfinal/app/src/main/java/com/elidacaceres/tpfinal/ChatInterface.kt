package com.elidacaceres.tpfinal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.background
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ChatScreen() {
    var userMessage by remember { mutableStateOf(TextFieldValue("")) }
    var messages by remember { mutableStateOf(listOf<Pair<String, Boolean>>()) } // true = User, false = Assistant

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Mostrar mensajes
        Box(modifier = Modifier.weight(1f).fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                messages.forEach { (message, isUser) ->
                    ChatBubble(message, isUser)
                }
            }
        }

        // Input para escribir mensaje
        Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
            BasicTextField(
                value = userMessage,
                onValueChange = { userMessage = it },
                modifier = Modifier
                    .weight(1f)
                    .background(Color.LightGray, shape = MaterialTheme.shapes.small)
                    .padding(8.dp)
            )
            Button(
                onClick = {
                    if (userMessage.text.isNotBlank()) {
                        messages = messages + (userMessage.text to true) // Mensaje del usuario
                        messages = messages + ("Mensaje de Asistente de prueba" to false) // Respuesta temporal
                        userMessage = TextFieldValue("") // Limpiar input
                    }
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Enviar")
            }
        }
    }
}

@Composable
fun ChatBubble(message: String, isUser: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        Text(
            text = message,
            modifier = Modifier
                .background(
                    if (isUser) Color.Blue else Color.Gray,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(8.dp),
            color = Color.White,
            textAlign = if (isUser) TextAlign.End else TextAlign.Start
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChatScreen() {
    ChatScreen()
}
