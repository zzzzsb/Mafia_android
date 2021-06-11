package com.example.myapplication.retrofit;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("userId")
    public String inputId;

    @SerializedName("passWd")
    public String inputPw;

    public String getInputId() {
        return inputId;
    }

    public String getInputPw() {
        return inputPw;
    }

    public void setInputId(String inputId) {
        this.inputId = inputId;
    }

    public void setInputPw(String inputPw) {
        this.inputPw = inputPw;
    }

    public LoginRequest(String inputId, String inputPw) {
        this.inputId = inputId;
        this.inputPw = inputPw;
    }
}
