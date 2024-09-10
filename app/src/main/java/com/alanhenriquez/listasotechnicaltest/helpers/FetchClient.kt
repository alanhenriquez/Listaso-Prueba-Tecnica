package com.alanhenriquez.listasotechnicaltest.helpers

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.TimeUnit

class FetchClient {

    // Inicializamos el cliente de OkHttp con un interceptor de logging (opcional)
    private val client: OkHttpClient

    init {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    // Método para realizar una solicitud GET
    fun getRequest(url: String, onResponse: (String?) -> Unit) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                onResponse(null)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    onResponse(response.body?.string())
                } else {
                    onResponse(null)
                }
            }
        })
    }

    // Método para realizar una solicitud POST con un cuerpo en JSON
    fun postRequest(url: String, jsonBody: String, onResponse: (String?) -> Unit) {
        val body = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), jsonBody)

        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                onResponse(null)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    onResponse(response.body?.string())
                } else {
                    onResponse(null)
                }
            }
        })
    }
}
