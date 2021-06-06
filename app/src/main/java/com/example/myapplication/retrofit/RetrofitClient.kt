package com.example.myapplication.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var retrofitClient: Retrofit? = null

    fun getClient(baseUrl: String): Retrofit? {
        retrofitClient = Retrofit.Builder()
            .baseUrl("http://3.34.179.145:8080/")
            .build()

        return retrofitClient
    }

}