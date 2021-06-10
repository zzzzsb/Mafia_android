package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.chat.MessageItem;
import com.example.myapplication.fragment.MainChatFragment;
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

    MainChatFragment mainChatFragment;

    Button connectButton;
    TextView roleTextView;
    List<MessageItem> messageItems = new ArrayList<>();

    private String userId;

    String nickname;
    StompAPI stompAPI = new StompAPI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainChatFragment = new MainChatFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.chatPlace, mainChatFragment).commit();
        connectButton = findViewById(R.id.connectButton);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserConnectionActivity.class);
                startActivity(intent);
            }
        });

        roleTextView = findViewById(R.id.yourRole_2);
        userId = getIntent().getStringExtra("userId");

        this.nickname = getIntent().getStringExtra("nickname");

        stompAPI.setJobListener( new StompAPI.JobListener() {
            @Override
            public void onJobReceived(String role) {
                roleTextView.setText(role);
            }
        });

        stompAPI.initStomp();
        stompAPI.onConnected(userId);

    }
}