package com.example.myapplication.retrofit;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("resultcode")
    public String resultCode;

    @SerializedName("nickname")
    public String nicname;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

}
