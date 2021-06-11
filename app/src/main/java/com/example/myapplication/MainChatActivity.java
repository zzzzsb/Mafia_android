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
import com.example.myapplication.chat.RoleMessage;
import com.example.myapplication.stomp.StompAPI;

import java.util.ArrayList;
import java.util.List;

public class MainChatActivity extends AppCompatActivity {
    Button connectButton;

    static final String KEY_DATA = "KEY_DATA";
        EditText et;
        TextView roleTextView;
        TextView wordTextView;
        TextView wordMafia;
        Button text_mafia;
        TextView text_mafia_content;
        Button logout;

        List<MessageItem> messageItems = new ArrayList<>();
        ChatAdapter adapter;
        RecyclerView recyclerView;

        private String userId;

        String nickname;
        String role;
        String word;
        StompAPI stompAPI = new StompAPI();

        boolean flag = false;

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
            wordTextView = findViewById(R.id.word);
            wordMafia = findViewById(R.id.word_mafia);
            text_mafia = findViewById(R.id.text_mafia);
            text_mafia_content = findViewById(R.id.text_mafia_content);
            logout = findViewById(R.id.logout);

            if (savedInstanceState != null) {
              nickname = savedInstanceState.getString("nickname");
              role = savedInstanceState.getString("role");
              word = savedInstanceState.getString("word");
              roleTextView.setText(role);
              wordTextView.setText(word);
            }

            et=findViewById(R.id.et);
            recyclerView = findViewById(R.id.chatRecycle);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainChatActivity.this, LinearLayoutManager.VERTICAL, false));

            userId = getIntent().getStringExtra("userId");

            this.nickname = getIntent().getStringExtra("nickname");
            setAdapter(recyclerView);

            stompAPI.setJobListener( new StompAPI.JobListener() {
                @Override
                public void onJobReceived(RoleMessage roleMessage) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            roleTextView.setText(roleMessage.getRole());
                            if(roleMessage.getRole().contains("사회자")) {
                                text_mafia.setVisibility(View.VISIBLE);
                                text_mafia_content.setVisibility(View.VISIBLE);
                            }
                            wordTextView.setText(roleMessage.getWord());
                            if(roleMessage.getRole().contains("마피아")) {
                                wordMafia.setText("제시어 확인 불가");
                            }
                        }
                    });
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

            text_mafia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stompAPI.setMafiaListener(new StompAPI.MafiaListener() {
                        @Override
                        public void onMafiaReceived(String mafia) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    text_mafia_content.setText(mafia);
                                }
                            });
                        }
                    });
                }
            });

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stompAPI.onDisconnect();
                    Intent intent = new Intent(MainChatActivity.this, LoginActivity.class);
                    startActivity(intent);
                    MainChatActivity.this.finish();
                }
            });

            if(flag==false) {
                flag = true;
                stompAPI.initStomp();
                stompAPI.onConnected(userId);
            }
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


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("nickname", this.nickname);
        outState.putString(KEY_DATA, this.roleTextView.getText().toString());
        outState.putString("word", this.wordTextView.getText().toString());
    }
}