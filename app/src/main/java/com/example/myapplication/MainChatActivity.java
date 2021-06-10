package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.content.Context;
import android.widget.Button;

import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.chat.ChatAdapter;
import com.example.myapplication.chat.MessageItem;
import com.example.myapplication.stomp.StompAPI;

import java.util.ArrayList;
import java.util.List;

public class MainChatActivity extends AppCompatActivity {
    Button connectButton;

        EditText et;
        TextView roleTextView;

        List<MessageItem> messageItems = new ArrayList<>();
        ChatAdapter adapter;
        RecyclerView recyclerView;

        private String userId;

        String nickname;
        StompAPI stompAPI = new StompAPI();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_chat);

            connectButton = findViewById(R.id.connectButton);
            connectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainChatActivity.this, UserConnectionActivity.class);
                    startActivity(intent);
                }
            });

            roleTextView = findViewById(R.id.yourRole_2);

            et=findViewById(R.id.et);
            recyclerView = findViewById(R.id.chatRecycle);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainChatActivity.this, LinearLayoutManager.VERTICAL, false));

            userId = getIntent().getStringExtra("userId");

            stompAPI.setStartListener2(new StompAPI.StartListener() {
                @Override
                public void onConnected(String nickName) {
                    MainChatActivity.this.nickname = nickName;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setAdapter(recyclerView);
                        }
                    });

                }
            });

            stompAPI.setJobListener( new StompAPI.JobListener() {
                @Override
                public void onJobReceived(String role) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            roleTextView.setText(role);
                        }
                    });
                    //roleTextView.setText(role);
                }
            });

            stompAPI.setAllChatListener(new StompAPI.ChatListener() {
                @Override
                public void onMessageReceived(MessageItem messageItem) {
                    //System.out.println(messageItems.size());
                    messageItems.add(messageItem);

                    //리스트뷰를 갱신
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("LISTSIZE: " + messageItems.size());
                            adapter.notifyDataSetChanged();
                            recyclerView.scrollToPosition(messageItems.size()-1);
                        }
                    });

                }
            });

            stompAPI.initStomp();
            stompAPI.onConnected(userId);

    }

    public void clickSend(View view){//firebase DB에 저장할 값들(네임, 메세지, 시간)
            String message= et.getText().toString();
            stompAPI.sendMessage(nickname, message);

            //EditText에 있는 글씨 지우기
            et.setText("");

            //소프트키패드를 안보이도록..
            InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

    }

    private void setAdapter(RecyclerView recyclerView) {
            adapter = new ChatAdapter(messageItems , nickname);
            recyclerView.setAdapter(adapter);
    }
}