import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.elidacaceres.tpfinal.R
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class) // Activar la API experimental
@Composable
fun ChatScreen(navController: NavController) {
    var userMessage by remember { mutableStateOf(TextFieldValue("")) }
    var messages by remember { mutableStateOf(listOf<Pair<String, Boolean>>()) } // true = User, false = Assistant

    Column(modifier = Modifier.fillMaxSize()) {
        // Header con botón de retroceso
        TopAppBar(
            title = {
                Text(
                    text = "UAAbot",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.navigate("home") }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFB71C1C)) // Color rojo oscuro
        )

        // Fondo con marca de agua (logo)
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.logo_2),
                contentDescription = "Logo de la app",
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(alpha = 0.1f) // Marca de agua con transparencia
                    .align(Alignment.Center)
            )

            // Mostrar mensajes
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
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
                        modifier = Modifier
                            .padding(start = 8.dp)
                    ) {
                        Text("Enviar") // Texto rojo oscuro
                    }
                }
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
    // Usamos un NavController simulado para la vista previa
    ChatScreen(navController = rememberNavController())
}
