package com.kma.onleethryy.activity.splash;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.kma.onleethryy.R;
import com.kma.onleethryy.activity.login.LoginActivity;
import com.kma.onleethryy.activity.mainScreen.MainScreenActivity;
import com.kma.onleethryy.api.APIClient;
import com.kma.onleethryy.api.APIInterface;
import com.kma.onleethryy.utils.AppUtils;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    private final int INTENT_AUTHENTICATE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPreferences = getSharedPreferences("config" , Context.MODE_PRIVATE);

        if (sharedPreferences.contains("pin") && sharedPreferences.getBoolean("pin" , false)){
            KeyguardManager km = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            Intent authIntent = km.createConfirmDeviceCredentialIntent("PIN AUTHENTICATE", "Nhập mã pin");
            startActivityForResult(authIntent, INTENT_AUTHENTICATE);
        }else if(sharedPreferences.contains("fingerPrint") && sharedPreferences.getBoolean("fingerPrint" , false)){
            Log.d("TAG1432", "onCreate: 2");
            Executor executor = ContextCompat.getMainExecutor(this);
            final BiometricPrompt biometricPrompt = new BiometricPrompt(SplashActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                    goToLogin();
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
        }else {
            new Thread(()->{
                try {
                    Thread.sleep(2000);
                    Intent intent = new Intent(SplashActivity.this , LoginActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_AUTHENTICATE) {
            if (resultCode == RESULT_OK) {
                afterAuthentic();
                Toast.makeText(getApplicationContext(), "Xác thực mã pin thành công", Toast.LENGTH_SHORT).show();
            } else {
                goToLogin();
                Toast.makeText(getApplicationContext(), "Xác thực mã pin không thành công", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void afterAuthentic(){
        String id = sharedPreferences.getString("id" , "");
        String password = sharedPreferences.getString("password" , "");
        APIClient.getClient()
                .create(APIInterface.class)
                .getUserLogin(new APIInterface.postLoginObject(id , password))
                .enqueue(new Callback<APIInterface.returnPostLogin>() {
                    @Override
                    public void onResponse(Call<APIInterface.returnPostLogin> call, Response<APIInterface.returnPostLogin> response) {
                        //post thanh cong
                        if (response.body() != null && response.body().isSuccess()){
                            //dang nhap thanh cong
                            //luu static string
                            AppUtils.id = id;
                            AppUtils.password = password;
                            AppUtils.idUser = response.body().getUserID();
                            AppUtils.token = response.body().getToken();
                            Intent intent = new Intent(SplashActivity.this, MainScreenActivity.class);
                            intent.putExtra("user_id" , response.body().getUserID());
                            intent.putExtra("token" , response.body().getToken());
                            intent.putExtra("name" , response.body().getName());
                            startActivity(intent);
                            finish();
                        }else {
                            //dang nhap that bai

                        }
                    }

                    @Override
                    public void onFailure(Call<APIInterface.returnPostLogin> call, Throwable t) {
                        //dang nhap sai

                    }
                });
    }

    private void goToLogin(){
        Intent intent = new Intent(SplashActivity.this , LoginActivity.class);
        startActivity(intent);
        finish();
    }
}