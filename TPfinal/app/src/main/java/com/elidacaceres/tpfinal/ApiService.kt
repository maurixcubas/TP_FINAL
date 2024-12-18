package com.elidacaceres.tpfinal

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// Data classes para los cuerpos y respuestas de las solicitudes
data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val success: Boolean, val message: String)

// Clase para crear un hilo
data class ThreadResponse(val thread_id: String, val created_at: String)

// Clase para enviar un mensaje
data class MessageRequest(val content: String)
data class SendMessageResponse(val assistant_reply: String) // Renombrado para claridad

// Clase para obtener hilos
data class ThreadsResponse(val thread_id: String, val created_at: String)

// Clase para obtener mensajes de un hilo
data class MessageHistoryResponse(val role: String, val content: String, val created_at: String)

// Clase para registro
data class RegisterRequest(
    val first_name: String,
    val last_name: String,
    val email: String,
    val phone: String,
    val password: String
)

data class RegisterResponse(val success: Boolean, val message: String)

// Interfaz de Retrofit
interface ApiService {
    @POST("/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("/api/threads")
    suspend fun createThread(): Response<ThreadResponse>

    @POST("/api/threads/{thread_id}/messages")
    suspend fun sendMessage(
        @Path("thread_id") threadId: String,
        @Body messageRequest: MessageRequest
    ): Response<SendMessageResponse> // Uso del nombre corregido

    @GET("/api/threads")
    suspend fun getThreads(): Response<List<ThreadsResponse>>

    @GET("/api/threads/{thread_id}/messages")
    suspend fun getMessages(@Path("thread_id") threadId: String): Response<List<MessageHistoryResponse>>

    @DELETE("/api/threads/{thread_id}")
    suspend fun deleteThread(@Path("thread_id") threadId: String): Response<Unit>
}
