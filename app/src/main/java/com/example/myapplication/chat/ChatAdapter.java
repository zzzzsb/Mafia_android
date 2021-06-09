package com.example.myapplication.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.UserConnectionAdapter;
import com.example.myapplication.retrofit.User;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<MessageItem> messageList = null;

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvName;
        TextView tvMsg;

        ViewHolder(View itemView){
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvMsg = itemView.findViewById(R.id.tv_msg);
        }
    }

    public ChatAdapter(List<MessageItem> messageItems) {
        this.messageList = messageItems;
    }
    @Override
    public int getItemViewType(int position) {
        if(messageList.get(position).getNickname().equals("수빈")) return 0;
        else return 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        //메세지가 내 메세지인지??
        if(viewType == 0){
            itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_msgbox,parent,false);
        }else {
            itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.other_msgbox,parent,false);
        }
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position){
        MessageItem item = messageList.get(position);
        if(viewHolder instanceof ViewHolder) {
            ((ViewHolder) viewHolder).tvName.setText(item.getNickname());
            ((ViewHolder) viewHolder).tvMsg.setText(item.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }


}