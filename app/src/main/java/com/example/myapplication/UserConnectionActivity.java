package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.adapter.UserConnectionAdapter;
import com.example.myapplication.retrofit.RetrofitClient;
import com.example.myapplication.retrofit.User;
import com.example.myapplication.stomp.StompAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserConnectionActivity extends AppCompatActivity {

    private List<User> userList;
    private String userId;

    private RetrofitClient retrofitClient;
    private com.example.myapplication.retrofit.initMyApi initMyApi;

    RecyclerView recyclerView;
    UserConnectionAdapter adapter;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_connection);

        recyclerView = findViewById(R.id.userRecycle);
        button = findViewById(R.id.returntoGameBtn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserConnectionActivity.this, WatingRoomActivity.class);
                startActivity(intent);
            }
        });

         //retrofit 생성
        retrofitClient = RetrofitClient.getInstance();
        initMyApi = RetrofitClient.getRetrofitInterface();

        initMyApi.getAllUserList().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                userList = response.body();

                recyclerView.setLayoutManager(new LinearLayoutManager(UserConnectionActivity.this, LinearLayoutManager.VERTICAL, false));
                setAdapter(recyclerView);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });


        StompAPI stompAPI = new StompAPI();
        stompAPI.setStartListener1(new StompAPI.StartListener() {
            @Override
            public void onConnected(String nickName) {
                initMyApi.getAllUserList().enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        UserConnectionActivity.this.userList = userList;
                        UserConnectionActivity.this.adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void setAdapter(RecyclerView recyclerView) {
        adapter = new UserConnectionAdapter(userList);
        recyclerView.setAdapter(adapter);
    }
}
