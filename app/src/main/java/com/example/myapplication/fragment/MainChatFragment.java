package com.example.myapplication.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.chat.ChatAdapter;
import com.example.myapplication.chat.MessageItem;
import com.example.myapplication.stomp.StompAPI;

import java.util.ArrayList;
import java.util.List;

public class MainChatFragment extends Fragment {

    View view;

    EditText et;
    TextView roleTextView;

    List<MessageItem> messageItems = new ArrayList<>();
    ChatAdapter adapter;
    RecyclerView recyclerView;
    Button sendBtn;

    private String userId;

    String nickname;
    StompAPI stompAPI = new StompAPI();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main_chat, container, false);

        et= view.findViewById(R.id.et);
        sendBtn = view.findViewById(R.id.sendBtn);
        recyclerView = view.findViewById(R.id.chatRecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        userId = getActivity().getIntent().getStringExtra("userId");

        this.nickname = getActivity().getIntent().getStringExtra("nickname");
        setAdapter(recyclerView);

        stompAPI.setAllChatListener(new StompAPI.ChatListener() {
            @Override
            public void onMessageReceived(MessageItem messageItem) {
                //System.out.println(messageItems.size());
                messageItems.add(messageItem);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        recyclerView.scrollToPosition(messageItems.size()-1);
                    }
                });
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSend(view);
            }
        });

        stompAPI.initStomp();
        stompAPI.onConnected(userId);

        return view;
    }

    public void clickSend(View view){
        String message= et.getText().toString();
        stompAPI.sendMessage(nickname, message);

        //EditText에 있는 글씨 지우기
        et.setText("");

        //소프트키패드를 안보이도록..
        InputMethodManager imm=(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),0);

    }

    private void setAdapter(RecyclerView recyclerView) {
        adapter = new ChatAdapter(messageItems , nickname);
        recyclerView.setAdapter(adapter);
    }

}
