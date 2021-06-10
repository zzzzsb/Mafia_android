package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.stomp.StompAPI;

public class WatingRoomActivity extends AppCompatActivity {

    private String userId;
    Button connectButton, startButton;
    StompAPI stompAPI = new StompAPI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wating_room);

        userId = getIntent().getStringExtra("userId");

        connectButton = findViewById(R.id.userConnection);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WatingRoomActivity.this, UserConnectionActivity.class);
                startActivity(intent);
            }
        });

        startButton = findViewById(R.id.startGame);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WatingRoomActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        stompAPI.initStomp();
        stompAPI.onConnected(userId);
    }
}