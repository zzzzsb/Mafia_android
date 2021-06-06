package com.example.myapplication.retrofit

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.POST

interface RetrofitService {
    //post
    // 매개변수를 미리 정해두는 방식
    @POST("login")
    fun postRequest(
        @Field("id") id: String,
        @Field("pw") pw: String
    ): Call<ResponseDTO>
}