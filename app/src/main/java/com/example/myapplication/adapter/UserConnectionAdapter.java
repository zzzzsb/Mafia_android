package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.UserConnectionActivity;
import com.example.myapplication.retrofit.User;

import java.util.List;

public class UserConnectionAdapter extends RecyclerView.Adapter<UserConnectionAdapter.ViewHolder> {

    private List<User> userList;

    public UserConnectionAdapter(List<User> userList) {
        this.userList = userList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nicknameView;
        ImageView statusView;

        ViewHolder(View itemView){
            super(itemView);
            nicknameView = itemView.findViewById(R.id.nicknameView);
            statusView = itemView.findViewById(R.id.statusView);
        }
    }

    @Override
    public UserConnectionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public int getItemCount(){
        return userList.size();
    }

    @Override
    public void onBindViewHolder(final UserConnectionAdapter.ViewHolder holder, final int position){
        try{
            holder.nicknameView.setText(userList.get(position).getNickName());
            //holder.statusView.setText(dangerList.get(position).getBSSHNM());

        } catch (Exception e) {System.out.println(e);}
    }
}
