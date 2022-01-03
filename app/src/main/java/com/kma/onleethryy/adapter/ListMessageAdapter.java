package com.kma.onleethryy.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kma.onleethryy.api.APIInterface;
import com.kma.onleethryy.databinding.ItemMessageBinding;
import com.kma.onleethryy.utils.AppUtils;

import java.util.List;

public class ListMessageAdapter extends RecyclerView.Adapter<ListMessageAdapter.ViewHolder> {

    Context context;
    List<APIInterface.returnMessage> list;

    public ListMessageAdapter(Context context , List<APIInterface.returnMessage> list){
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMessageBinding binding = ItemMessageBinding
                .inflate(LayoutInflater.from(parent.getContext()) , parent , false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        APIInterface.returnMessage message = list.get(position);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) holder.textView.getLayoutParams();
        if (message.getFromId().equals(AppUtils.idUser)){
            //tin nhan cua nguoi dung
            params.gravity = Gravity.END;
        }else {
            //tin nhan cua nguoi khac
            params.gravity = Gravity.START;
        }
        holder.textView.setLayoutParams(params);
        holder.textView.setText(message.getBody());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(ItemMessageBinding binding) {
            super(binding.getRoot());
            textView = binding.body;
        }
    }
}
