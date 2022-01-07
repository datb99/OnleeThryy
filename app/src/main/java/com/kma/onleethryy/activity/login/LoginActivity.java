package com.kma.onleethryy.activity.login;

import android.Manifest;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.InputType;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.kma.onleethryy.R;
import com.kma.onleethryy.api.APIClient;
import com.kma.onleethryy.api.APIInterface;
import com.kma.onleethryy.databinding.ActivityLoginBinding;

import java.io.File;
import java.io.InputStream;
import java.util.concurrent.Executor;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    LoginViewModel viewModel;
    private final int INTENT_AUTHENTICATE = 10;
    private final int PICK_IMAGE = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this , R.layout.activity_login);
        viewModel = new LoginViewModel(this);
        binding.setViewModel(viewModel);
        binding.editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        permission();
    }

    private void permission(){
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
        requestPermissions(permissions , 0);
    }

    public void startPINAuthenticate(){
        KeyguardManager km = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        Intent authIntent = km.createConfirmDeviceCredentialIntent("PIN AUTHENTICATE", "Nhập mã pin");
        startActivityForResult(authIntent, INTENT_AUTHENTICATE);
    }

    public void startFingerPrint(){
        Executor executor = ContextCompat.getMainExecutor(this);
        final BiometricPrompt biometricPrompt = new BiometricPrompt(LoginActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }


            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                afterAuthentic();
                Toast.makeText(getApplicationContext(), "Xác thực vân tay thành công", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Xác thực vân tay không thành công", Toast.LENGTH_SHORT).show();
            }
        });
        final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("FINGER PRINT AUTHENTICATE")
                .setDescription("Xác thực bằng vân tay").setNegativeButtonText("Cancel").build();

        biometricPrompt.authenticate(promptInfo);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_AUTHENTICATE) {
            if (resultCode == RESULT_OK) {
                afterAuthentic();
                Toast.makeText(getApplicationContext(), "Xác thực mã pin thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Xác thực mã pin không thành công", Toast.LENGTH_SHORT).show();

            }
        }

    }

    private void afterAuthentic(){
        String id = viewModel.sharedPreferences.getString("id" , "");
        String password = viewModel.sharedPreferences.getString("password" , "");
        viewModel.startLogin(id , password);
    }




}
