package com.example.myapplication.retrofit;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("result")
    public String resultCode;

    @SerializedName("nicname")
    public String nicname;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getToken() {
        return nicname;
    }

    public void setToken(String token) {
        this.nicname = nicname;
    }

}
