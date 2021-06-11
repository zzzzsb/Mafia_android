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

    private String userId;
    Button connectButton, startButton;
    StompAPI stompAPI = new StompAPI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userId = getIntent().getStringExtra("userId");

        connectButton = findViewById(R.id.userConnection);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserConnectionActivity.class);
                startActivity(intent);
            }
        });

        startButton = findViewById(R.id.startGame);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainChatActivity.class);
                startActivity(intent);
            }
        });

        stompAPI.initStomp();
        stompAPI.onConnected(userId);
    }
}