package com.example.myapplication.stomp;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.dynamicanimation.animation.SpringAnimation;

import com.example.myapplication.chat.MessageItem;
import com.example.myapplication.chat.RoleMessage;
import com.example.myapplication.retrofit.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.disposables.Disposable;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.StompHeader;

public class StompAPI {

    private StompClient mStompClient;

    String BASE_URL = "ws://ec2-52-78-141-185.ap-northeast-2.compute.amazonaws.com:8080/ws/websocket";
    //String BASE_URL = "ws://10.0.2.2:8080/ws/websocket";
    String TAG = "TAG";
    boolean isUnexpectedClosed;

    private List<StompHeader> headerList = new ArrayList<>();
    private List<User> userList;
    Gson gson = new Gson();

    private StartListener startListener1;
    private StartListener startListener2;
    private JobListener jobListener;
    private ChatListener allChatListener;
    private MafiaListener mafiaListener;

    @SuppressLint("CheckResult")
    public void initStomp() {
        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, BASE_URL);
        mStompClient.connect();

    }

    @SuppressLint("CheckResult")
    public void onConnected(String userId) {

        mStompClient.topic("/topic/public/start").subscribe(topicMessage -> {

            if(startListener1!=null) {
                startListener1.onConnected(topicMessage.getPayload());
            }

        });

         mStompClient.topic("/user/queue/role").subscribe(message -> {
           Log.d("ROLE", message.getPayload());
           RoleMessage roleMessage = gson.fromJson(message.getPayload(), new TypeToken<RoleMessage>(){}.getType());

           if(jobListener!=null) {
               Log.d("ROLE", message.getPayload());
               jobListener.onJobReceived(roleMessage);
           }
        });

        mStompClient.topic("/topic/public/day").subscribe(topicMessage -> {

            MessageItem messageItem = gson.fromJson(topicMessage.getPayload(), new TypeToken<MessageItem>(){}.getType());
            Log.d("CHAT", topicMessage.getPayload());
            if(allChatListener!=null) {
                allChatListener.onMessageReceived(messageItem);
            }
        });

        mStompClient.topic("/user/queue/mafia").subscribe(stompMessage -> {
            if(mafiaListener!=null) {
                mafiaListener.onMafiaReceived(stompMessage.getPayload());
            }
        });

        JsonObject obj = new JsonObject();
        obj.addProperty("sender", userId);

        mStompClient.send("/app/chat.addUser", obj.toString()).subscribe();
        mStompClient.send("/app/role", userId).subscribe();
    }


    @SuppressLint("CheckResult")
    public void sendMessage(String user, String msg) {

        JsonObject obj = new JsonObject();
        obj.addProperty("sender", user);
        obj.addProperty("content", msg);
        mStompClient.send("/app/chat.sendMessage", obj.toString()).subscribe();
    }

    public void onDisconnect() {
        mStompClient.disconnect();
    }

    public void setStartListener1(StartListener listener) {
        this.startListener1 = listener;
    }

    public void setStartListener2(StartListener listener) {
        this.startListener2 = listener;
    }

    public void setJobListener(JobListener listener) {
        this.jobListener = listener;
    }

    public void setAllChatListener(ChatListener listener) {
        this.allChatListener = listener;
    }

    public void setMafiaListener(MafiaListener listener) {
        this.mafiaListener = listener;
    }

    public interface StartListener {
        void onConnected(String nickName);
    }

    public interface ChatListener {
        void onMessageReceived(MessageItem messageItem);
    }

    public interface JobListener {
        void onJobReceived(RoleMessage roleMessage);
    }

    public interface MafiaListener {
        void onMafiaReceived(String mafia);
    }


}
