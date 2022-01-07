package com.kma.onleethryy.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.kma.onleethryy.activity.chat.ChatActivity;
import com.kma.onleethryy.api.APIInterface;
import com.kma.onleethryy.databinding.ItemConversationBinding;
import com.kma.onleethryy.utils.AES;
import com.kma.onleethryy.utils.AppUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListChatAdapter extends RecyclerView.Adapter<ListChatAdapter.ViewHolder>{

    Context context;
    List<APIInterface.returnConversation> list;

    public ListChatAdapter(Context context , List<APIInterface.returnConversation> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemConversationBinding binding = ItemConversationBinding
                .inflate(LayoutInflater.from(parent.getContext()) , parent , false);
        return new ListChatAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        APIInterface.returnConversation conversation = list.get(position);
        Log.d("TAG1432", "onBindViewHolder: " + new Gson().toJson(conversation.getRecipientObj()));
        String key = "";
        //lay ten user trong conversation
        for (int i = 0 ; i < conversation.getRecipientObj().size() ; i ++){
            if (!conversation.getRecipientObj().get(i).getId().equals(AppUtils.idUser)){
                holder.name.setText(conversation.getRecipientObj().get(i).getName());
                key = AES.genKeyFromId(conversation.getRecipientObj().get(i).getId() , AppUtils.idUser);
                //set click
                int finalI = i;
                holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context , ChatActivity.class);
                        //truyen vao tham so
                        intent.putExtra("receiverId" , conversation.getRecipientObj().get(finalI).getId());
                        intent.putExtra("receiverName" , conversation.getRecipientObj().get(finalI).getName());
                        intent.putExtra("receiverAva" , "user.getAvatar()");
                        context.startActivity(intent);
                    }
                });
                break;
            }
        }
        //lay last message
        String encrypted = conversation.getLastMessage();
        String lastMessage = AES.decrypt(encrypted , key);
        holder.body.setText(lastMessage);
        //lay date
        holder.date.setText(conversation.getDate());
        //set avatar
        Glide.with(context).load("http://14.225.7.41:8686/avatar_hint.png").into(holder.avatar);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, body , date;
        LinearLayout linearLayout;
        CircleImageView avatar;

        public ViewHolder(ItemConversationBinding binding) {
            super(binding.getRoot());
            name = binding.name;
            date = binding.date;
            body = binding.body;
            linearLayout = binding.mLayout;
            avatar = binding.avatar;
        }
    }
}
