package com.kma.onleethryy.activity.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kma.onleethryy.activity.login.LoginActivity;

import com.kma.onleethryy.R;
import com.kma.onleethryy.api.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText etUsername, etPass, etPass2, etName;
    Button btRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etUsername);
        etPass = findViewById(R.id.etPass);
        etPass2 = findViewById(R.id.etPass2);
        etName = findViewById(R.id.etName);
        btRegister = findViewById(R.id.btRegister);

        btRegister.setOnClickListener(view -> {
            APIClient.getClient().create(APIInterface.class)
                    .registerUser(new APIInterface.postRegister(etUsername.getText().toString(), etPass.getText().toString(), etPass2.getText().toString(), etName.getText().toString()))
                    .enqueue(new Callback<APIInterface.returnRegister>() {
                        @Override
                        public void onResponse(@NonNull Call<APIInterface.returnRegister> call, @NonNull Response<APIInterface.returnRegister> response) {
                            if (response.body()!= null && response.body().isSuccess()) {
                                Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                RegisterActivity.this.startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            } else {
                                Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<APIInterface.returnRegister> call, @NonNull Throwable t) {
                            Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}