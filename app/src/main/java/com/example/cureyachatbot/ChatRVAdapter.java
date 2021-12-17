package com.example.cureyachatbot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatRVAdapter extends RecyclerView.Adapter {

    ArrayList<ChatModel>chatModelArrayList;
    Context context;

    public ChatRVAdapter(ArrayList<ChatModel> chatModelArrayList, Context context) {
        this.chatModelArrayList = chatModelArrayList;
        this.context = context;
    }
    @Override
    public int getItemViewType(int position) {
        String sender=chatModelArrayList.get(position).getSender();
        switch (sender)
        {
            case "user":
                return 0;
            case "bot":
                return 1;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType)
        {
            case 0:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_of_user,parent,false);
                return new UserViewHolder(view);
            case 1:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_of_bot,parent,false);
                return new BotViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatModel chatModel=chatModelArrayList.get(position);
        switch (chatModel.getSender())
        {
            case "user":
                ((UserViewHolder)holder).tv_user.setText(chatModel.getMessage());
                break;
            case "bot":
                ((BotViewHolder)holder).tv_bot.setText(chatModel.getMessage());
                break;
        }
    }



    @Override
    public int getItemCount() {
        return chatModelArrayList.size();
    }

    public static class BotViewHolder extends RecyclerView.ViewHolder{
        TextView tv_bot;
        public BotViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_bot=itemView.findViewById(R.id.tv_bot);
        }
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{
        TextView tv_user;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_user=itemView.findViewById(R.id.tv_user);
        }
    }

}
