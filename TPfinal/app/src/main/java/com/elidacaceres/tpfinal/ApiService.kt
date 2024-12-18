package com.elidacaceres.tpfinal

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

// Data classes para los cuerpos y respuestas de las solicitudes
data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val success: Boolean, val message: String)

data class RegisterRequest(
    val first_name: String,
    val last_name: String,
    val email: String,
    val phone: String,
    val password: String
)

data class RegisterResponse(val success: Boolean, val message: String)

interface ApiService {
    // Ruta para login
    @POST("/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    // Ruta para registro
    @POST("/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>
}