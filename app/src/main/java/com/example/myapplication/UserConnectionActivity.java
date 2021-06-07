package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.adapter.UserConnectionAdapter;
import com.example.myapplication.retrofit.User;

import java.util.List;

public class UserConnectionActivity extends AppCompatActivity {

    private List<User> userList;

    RecyclerView recyclerView;
    UserConnectionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_connection);

        Intent intent = getIntent();
        userList = intent.getParcelableArrayListExtra("userList");

        recyclerView = findViewById(R.id.userRecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        setAdapter(recyclerView);

    }

    private void setAdapter(RecyclerView recyclerView) {
        adapter = new UserConnectionAdapter(userList);
        recyclerView.setAdapter(adapter);
    }
}