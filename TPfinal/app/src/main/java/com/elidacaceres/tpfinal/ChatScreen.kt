import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import com.elidacaceres.tpfinal.MessageRequest
import com.elidacaceres.tpfinal.RetrofitInstance

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavController, initialThreadId: String?) {
    var userMessage by remember { mutableStateOf(TextFieldValue("")) }
    var messages by remember { mutableStateOf(listOf<Pair<String, String>>()) } // Pair(role, content)
    var threadId by remember { mutableStateOf(initialThreadId) }
    var isLoading by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    // Cargar mensajes existentes si hay un threadId
    LaunchedEffect(threadId) {
        threadId?.let { id ->
            coroutineScope.launch {
                try {
                    val response = RetrofitInstance.api.getMessages(id)
                    if (response.isSuccessful) {
                        messages = response.body()?.map { it.role to it.content } ?: emptyList()
                    }
                } catch (e: Exception) {
                    println("Error al cargar mensajes: ${e.message}")
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chat", style = MaterialTheme.typography.headlineMedium) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("chat_options") }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BasicTextField(
                    value = userMessage,
                    onValueChange = { userMessage = it },
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.LightGray)
                        .padding(8.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                    val cleanMessage = userMessage.text.trim()
                    if (cleanMessage.isNotEmpty()) {
                        // Mostrar mensaje del usuario directamente
                        messages = messages + ("user" to cleanMessage)

                        coroutineScope.launch {
                            isLoading = true
                            try {
                                // Si no hay threadId, crea uno nuevo
                                if (threadId == null) {
                                    val threadResponse = RetrofitInstance.api.createThread()
                                    if (threadResponse.isSuccessful) {
                                        threadId = threadResponse.body()?.thread_id
                                    }
                                }
                                // Enviar mensaje
                                threadId?.let { id ->
                                    val request = MessageRequest(content = cleanMessage)
                                    val response = RetrofitInstance.api.sendMessage(id, request)
                                    if (response.isSuccessful) {
                                        val reply = response.body()?.assistant_reply ?: "Sin respuesta"
                                        messages = messages + ("assistant" to reply)
                                    }
                                }
                            } catch (e: Exception) {
                                println("Error al enviar mensaje: ${e.message}")
                            } finally {
                                isLoading = false
                            }
                        }
                        userMessage = TextFieldValue("") // Limpiar input
                    }
                },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF800000), // Color de fondo del botón
                        contentColor = Color.White  // Color del texto
                    )
                ) {
                    Text("Enviar")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            messages.forEach { (role, content) ->
                ChatBubble(content, role == "user")
            }
            if (isLoading) {
                LoadingDotsAnimation()
            }
        }
    }

    // Scroll automático al final del chat
    LaunchedEffect(messages) {
        coroutineScope.launch {
            scrollState.animateScrollTo(scrollState.maxValue)
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
            color = Color.White,
            modifier = Modifier
                .background(if (isUser) Color.Blue else Color.Gray)
                .padding(8.dp)
        )
    }
}

@Composable
fun LoadingDotsAnimation() {
    val dotState = rememberInfiniteTransition()
    val dotOffset by dotState.animateFloat(
        initialValue = 0f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(3) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(Color.Gray, shape = MaterialTheme.shapes.small)
                    .padding(horizontal = dotOffset.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewChatScreen() {
    ChatScreen(navController = rememberNavController(), null)
}
