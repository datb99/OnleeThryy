package com.kma.onleethryy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kma.onleethryy.api.APIInterface;
import com.kma.onleethryy.databinding.ItemConversationBinding;
import com.kma.onleethryy.utils.AppUtils;

import java.util.List;

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
        //lay ten user trong conversation
        for (int i = 0 ; i < conversation.getRecipientObj().size() ; i ++){
            if (!conversation.getRecipientObj().get(i).getId().equals(AppUtils.idUser)){
                holder.name.setText(conversation.getRecipientObj().get(i).getName());
                break;
            }
        }
        //lay last message
        holder.body.setText(conversation.getLastMessage());
        //lay date
        holder.date.setText(conversation.getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, body , date;

        public ViewHolder(ItemConversationBinding binding) {
            super(binding.getRoot());
            name = binding.name;
            date = binding.date;
            body = binding.body;
        }
    }
}
