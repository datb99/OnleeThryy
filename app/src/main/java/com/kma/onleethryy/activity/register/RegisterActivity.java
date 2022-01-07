package com.kma.onleethryy.activity.register;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.kma.onleethryy.R;
import com.kma.onleethryy.api.APIClient;
import com.kma.onleethryy.api.APIInterface;
import com.kma.onleethryy.databinding.ActivityRegisterBinding;
import com.kma.onleethryy.object.ImageFilePath;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    String avatar = null;
    File file;
    private final int PICK_IMAGE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this , R.layout.activity_register);
        setContentView(binding.getRoot());
        binding.password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        binding.password2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        binding.buttonRegister.setOnClickListener((v)->{

            if (!binding.name.getText().toString().equals("")&&
            !binding.userName.getText().toString().equals("")&&
            !binding.password.getText().toString().equals("")&&
            !binding.password2.getText().toString().equals("")){
                if (binding.password.getText().toString().equals(binding.password2.getText().toString())){
                    postImage();
                }else {
                    Toast.makeText(getApplicationContext() , "Mật khẩu không trùng nhau ", Toast.LENGTH_SHORT).show();
                }

            }
        });

        binding.avatar.setOnClickListener((v)->{
            chooseAvatar();
        });
    }

    private void register(){
        APIInterface.postRegisterObject user = new APIInterface.postRegisterObject(
          binding.name.getText().toString(),
          binding.userName.getText().toString(),
          binding.password.getText().toString(),
          binding.password2.getText().toString(),
          avatar
        );
        Log.d("TAG1432", "register: " + new Gson().toJson(user));
        APIClient.getClient()
                .create(APIInterface.class)
                .register(user)
                .enqueue(new Callback<APIInterface.returnRegister>() {
                    @Override
                    public void onResponse(Call<APIInterface.returnRegister> call, Response<APIInterface.returnRegister> response) {
                        if (response.body().isSuccess()){
                            //dang ky thanh cong
                            Toast.makeText(getApplicationContext() , "Đăng ký thành công" , Toast.LENGTH_LONG).show();
                            finish();
                        }else {
                            //dang ky khong thanh cong
                            Toast.makeText(getApplicationContext() , "Đăng ký không thành công, userName này đã được sử dụng" , Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<APIInterface.returnRegister> call, Throwable t) {
                        Toast.makeText(getApplicationContext() , "Đăng ký lỗi, hãy kiểm tra lại mạng" , Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK){
            final Uri uri = data.getData();
            file = new File(ImageFilePath.getPath(getApplicationContext() , uri));
            Glide.with(getApplicationContext()).load(file).into(binding.avatar);
        }
    }

    private void chooseAvatar(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    private void postImage(){
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        APIClient.getClient().create(APIInterface.class).postImage(body)
                .enqueue(new Callback<APIInterface.returnURL>() {
                    @Override
                    public void onResponse(Call<APIInterface.returnURL> call, Response<APIInterface.returnURL> response) {
                        avatar = response.body().getUrl();
                        register();
                    }

                    @Override
                    public void onFailure(Call<APIInterface.returnURL> call, Throwable t) {
                        Log.d("TAG1432", "onResponse: " + t.toString());
                    }
                });
    }


}