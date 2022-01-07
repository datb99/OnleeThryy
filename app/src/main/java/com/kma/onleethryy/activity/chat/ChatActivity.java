package com.kma.onleethryy.activity.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.kma.onleethryy.R;
import com.kma.onleethryy.adapter.ListMessageAdapter;
import com.kma.onleethryy.api.APIClient;
import com.kma.onleethryy.api.APIInterface;
import com.kma.onleethryy.databinding.ActivityChatBinding;
import com.kma.onleethryy.utils.AES;
import com.kma.onleethryy.utils.AppUtils;

import java.net.URISyntaxException;
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
    ListMessageAdapter adapter;
    Socket socket;
    String urlServer = "http://14.225.7.41:8686";

    String receiverId;
    String receiverName;
    String receiverAva;

    String key;

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

        //load du lieu cua nguoi nhan
        loadIntent();

        //tao ra key tu id
        key = AES.genKeyFromId(AppUtils.idUser , receiverId);

        //load du lieu doan chat
        loadChat();

        binding.sendButton.setOnClickListener((v)->{
            if (!binding.editText.getText().toString().equals("")){
                sendMessage();
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void loadIntent(){
        Intent intent = getIntent();
        receiverId = intent.getStringExtra("receiverId");
        receiverName = intent.getStringExtra("receiverName");
        receiverAva = intent.getStringExtra("receiverAva");
        binding.nameReceiver.setText(receiverName);
    }

    private void loadChat(){
        APIClient.getClient()
                .create(APIInterface.class)
                .getAllChat(AppUtils.token, receiverId)
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
        adapter = new ListMessageAdapter(getApplicationContext() , list , binding.recyclerView.getWidth() , key);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);
    }

    private void sendMessage(){
        String content = binding.editText.getText().toString();
        String encrypt = AES.encrypt(content , key);
        APIInterface.sendMessageObject message = new APIInterface
                .sendMessageObject(encrypt , receiverId);
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
        loadChat();
    };
}