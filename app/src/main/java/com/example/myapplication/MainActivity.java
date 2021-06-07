package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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

    private RetrofitClient retrofitClient;
    private com.example.myapplication.retrofit.initMyApi initMyApi;

    private List<User> userList = new ArrayList<>();
    private HashMap<User, Boolean> connectedUser = new HashMap<User, Boolean>();

    private String nickName;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //retrofit 생성
        retrofitClient = RetrofitClient.getInstance();
        initMyApi = RetrofitClient.getRetrofitInterface();

        //전체 user 목록 받아오기
        initMyApi.getAllUserList().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                userList = response.body();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });

        nickName = getIntent().getStringExtra("userId");


        StompAPI stompAPI = new StompAPI();
        stompAPI.initStomp();
        stompAPI.onConnected(nickName);

        button = findViewById(R.id.main_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserConnectionActivity.class);

                intent.putParcelableArrayListExtra("userList", (ArrayList<? extends Parcelable>) userList);

                startActivity(intent);
            }
        });

    }
}