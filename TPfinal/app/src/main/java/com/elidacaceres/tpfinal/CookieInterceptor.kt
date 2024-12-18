package com.elidacaceres.tpfinal

import okhttp3.Interceptor
import okhttp3.Response

object CookieInterceptor : Interceptor {
    private var cookies: String? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        // Agrega las cookies si existen
        cookies?.let {
            requestBuilder.addHeader("Cookie", it)
        }

        val response = chain.proceed(requestBuilder.build())

        // Guarda las cookies de la respuesta
        response.headers("Set-Cookie").forEach {
            cookies = it.split(";")[0]  // Guarda solo la cookie principal
        }

        return response
    }
}
