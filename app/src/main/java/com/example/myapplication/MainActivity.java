package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.retrofit.RetrofitClient;
import com.example.myapplication.retrofit.User;
import com.example.myapplication.stomp.StompAPI;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private List<User> userList;

    private String userId;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userId = getIntent().getStringExtra("userId");

        StompAPI stompAPI = new StompAPI();
        stompAPI.initStomp();
        stompAPI.onConnected(userId);

        button = findViewById(R.id.main_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserConnectionActivity.class);
                startActivity(intent);
            }
        });
    }
}