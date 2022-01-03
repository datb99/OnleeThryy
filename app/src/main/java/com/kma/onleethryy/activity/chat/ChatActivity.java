package com.kma.onleethryy.activity.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.kma.onleethryy.R;
import com.kma.onleethryy.activity.mainScreen.MainScreenActivity;
import com.kma.onleethryy.adapter.ListMessageAdapter;
import com.kma.onleethryy.api.APIClient;
import com.kma.onleethryy.api.APIInterface;
import com.kma.onleethryy.databinding.ActivityChatBinding;
import com.kma.onleethryy.utils.AppUtils;

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    ActivityChatBinding binding;
    String userId = "61cc6f7687097ff4963ffe57";
    ListMessageAdapter adapter;
    Socket socket;
    String urlServer = "http://14.225.7.41:8686";

    {
        try {
            socket = IO.socket(urlServer);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        socket.connect();
        socket.on("messages" , onNewMessage);
        binding = DataBindingUtil.setContentView(this , R.layout.activity_chat);

        loadChat();

        binding.sendButton.setOnClickListener((v)->{
            if (!binding.editText.getText().toString().equals("")){
                sendMessage();
            }
        });

    }

    private void loadChat(){
        APIClient.getClient()
                .create(APIInterface.class)
                .getAllChat(AppUtils.token, userId)
                .enqueue(new Callback<List<APIInterface.returnMessage>>() {
                    @Override
                    public void onResponse(Call<List<APIInterface.returnMessage>> call, Response<List<APIInterface.returnMessage>> response) {
                        initMessage(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<APIInterface.returnMessage>> call, Throwable t) {
                    }
                });
    }

    private void initMessage(List<APIInterface.returnMessage> list){
        Collections.reverse(list);
        adapter = new ListMessageAdapter(getApplicationContext() , list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);
    }

    private void sendMessage(){
        APIInterface.sendMessageObject message = new APIInterface
                .sendMessageObject(binding.editText.getText().toString() , userId);
        AppUtils.hideKeyBoard(this);
        binding.editText.setText("");
        binding.editText.clearFocus();
        Log.d("TAG1432", "onResponse: object " + new Gson().toJson(message));
        APIClient.getClient()
                .create(APIInterface.class)
                .sendMessage(AppUtils.token , message)
                .enqueue(new Callback<APIInterface.returnSendMessage>() {
                    @Override
                    public void onResponse(Call<APIInterface.returnSendMessage> call, Response<APIInterface.returnSendMessage> response) {
                        Log.d("TAG1432", "onResponse: send message" + new Gson().toJson(response.body()));
                    }

                    @Override
                    public void onFailure(Call<APIInterface.returnSendMessage> call, Throwable t) {
                        Log.d("TAG1432", "onResponse: send failed");
                    }
                });
    }

    private Emitter.Listener onNewMessage = (args)->{
        //chi mang
        Log.d("TAG1432", "message change: ");
    };
}