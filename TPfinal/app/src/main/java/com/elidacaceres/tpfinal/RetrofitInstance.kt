package com.elidacaceres.tpfinal

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    private const val BASE_URL = "https://uaa-chatbot.onrender.com"  // Cambia con tu URL

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS) // Tiempo para conectar
        .readTimeout(30, TimeUnit.SECONDS) // Tiempo para leer datos
        .writeTimeout(30, TimeUnit.SECONDS) // Tiempo para enviar datos
        .addInterceptor(CookieInterceptor)  // Interceptor de cookies
        .build()

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
