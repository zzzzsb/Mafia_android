package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WatingRoomActivity extends AppCompatActivity {

    Button connectButton, startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wating_room);

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
    }
}