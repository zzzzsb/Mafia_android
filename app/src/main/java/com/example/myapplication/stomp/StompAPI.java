package com.example.myapplication.stomp;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.myapplication.retrofit.User;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.StompHeader;

public class StompAPI {

    private StompClient mStompClient;
    String BASE_URL = "wss://13.209.72.43:8080/wss/websocket";
    ;
    String TAG = "TAG";
    boolean isUnexpectedClosed;

    private List<StompHeader> headerList = new ArrayList<>();

    @SuppressLint("CheckResult")
    public void initStomp() {
        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, BASE_URL);

        mStompClient.lifecycle().subscribe(lifecycleEvent -> {
            switch (lifecycleEvent.getType()) {
                case OPENED:
                    Log.d(TAG, "Stomp connection opened");
                    break;
                case ERROR:
                    Log.e(TAG, "Error", lifecycleEvent.getException());
                    if (lifecycleEvent.getException().getMessage().contains("EOF")) {
                        isUnexpectedClosed = true;
                    }
                    break;
                case CLOSED:
                    Log.d(TAG, "Stomp connection closed");
                    if (isUnexpectedClosed) {
                        /**
                         * EOF Error
                         */
                        initStomp();
                        isUnexpectedClosed = false;
                    }
                    break;
            }
        });

        mStompClient.connect();
    }

    public void onConnected(String nickname) {
        Disposable subscribe = mStompClient.topic("/topic/public").subscribe(topicMessage -> {
            Log.d(TAG, topicMessage.getPayload());
        });

        JsonObject obj = new JsonObject();
        obj.addProperty("sender",nickname);
        obj.addProperty("type", "JOIN");

        mStompClient.send("/app/char.addUser", obj.toString());
    }

    public void sendMessage(User user, String msg) {

        JsonObject obj = new JsonObject();
        obj.addProperty("sender", user.getNickName());
        obj.addProperty("content", msg);
        obj.addProperty("type", "CHAT");
        mStompClient.send("/app/chat.sendMessage", obj.toString()).subscribe();

        mStompClient.disconnect();
    }

    public void onDisconnect() {
        mStompClient.disconnect();
    }
}