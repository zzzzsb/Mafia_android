package com.example.myapplication.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface initMyApi {
    //@통신 방식("통신 API명")
    @POST("/login")
    Call<LoginResponse> getLoginResponse(@Body LoginRequest loginRequest);

    @GET("/userList")
    Call<List<User>> getAllUserList();
}
