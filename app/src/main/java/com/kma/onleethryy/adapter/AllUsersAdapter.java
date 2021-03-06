package com.kma.onleethryy.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kma.onleethryy.activity.chat.ChatActivity;
import com.kma.onleethryy.activity.mainScreen.MainScreenActivity;
import com.kma.onleethryy.api.APIClient;
import com.kma.onleethryy.api.APIInterface;
import com.kma.onleethryy.databinding.ItemAllUsersBinding;
import com.kma.onleethryy.utils.AppUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllUsersAdapter extends RecyclerView.Adapter<AllUsersAdapter.ViewHolder> {

    Context context;
    List<APIInterface.returnAllUsers> listUser;

    public AllUsersAdapter(Context context , List<APIInterface.returnAllUsers> listUser){
        this.context = context;
        this.listUser = listUser;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAllUsersBinding binding = ItemAllUsersBinding
                .inflate(LayoutInflater.from(parent.getContext()) , parent , false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        APIInterface.returnAllUsers user = listUser.get(position);
        if (user.getId().equals(AppUtils.idUser)){
            AppUtils.setUser(user);
            listUser.remove(user);
        }else {
            holder.textView.setText(user.getName());
        }
        Glide.with(context)
                .load(APIClient.BASE_URL + listUser.get(position).getAvatar())
                .into(holder.avatar);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , ChatActivity.class);
                //truyen vao tham so
                intent.putExtra("receiverId" , user.getId());
                intent.putExtra("receiverName" , user.getName());
                intent.putExtra("receiverAva" , user.getAvatar());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        LinearLayout linearLayout;
        CircleImageView avatar;

        public ViewHolder(ItemAllUsersBinding binding) {
            super(binding.getRoot());
            textView = binding.textView;
            linearLayout = binding.mLayout;
            avatar = binding.avatar;
        }
    }
}
