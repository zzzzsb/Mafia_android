package com.example.myapplication.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.stomp.StompAPI;

public class MainChatFragment extends Fragment {

    EditText et;
    String nickname;
    StompAPI stompAPI = new StompAPI();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_chat, container, false);

    }

    public void clickSend(View view){//firebase DB에 저장할 값들(네임, 메세지, 시간)
        String message= et.getText().toString();
        stompAPI.sendMessage(nickname, message);

        //EditText에 있는 글씨 지우기
        et.setText("");

        //소프트키패드를 안보이도록..
        InputMethodManager imm=(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),0);

    }


}
