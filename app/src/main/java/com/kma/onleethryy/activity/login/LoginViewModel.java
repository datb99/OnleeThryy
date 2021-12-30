package com.kma.onleethryy.activity.login;

import android.content.Intent;
import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.google.gson.Gson;
import com.kma.onleethryy.BR;
import com.kma.onleethryy.activity.mainScreen.MainScreenActivity;
import com.kma.onleethryy.api.APIClient;
import com.kma.onleethryy.api.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginViewModel extends BaseObservable {

    private String id , password;
    private ObservableField<String> message = new ObservableField<>();
    private LoginActivity activity;

    public LoginViewModel(LoginActivity activity){
        this.activity = activity;
    }



    @Bindable
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    public void onClickLogin(){
        APIClient.getClient()
                .create(APIInterface.class)
                .getUserLogin(new APIInterface.postLoginObject(getId() , getPassword()))
                .enqueue(new Callback<APIInterface.returnPostLogin>() {
                    @Override
                    public void onResponse(Call<APIInterface.returnPostLogin> call, Response<APIInterface.returnPostLogin> response) {
                        //post thanh cong
                        if (response.body().isSuccess()){
                            //dang nhap thanh cong
                            Intent intent = new Intent(activity , MainScreenActivity.class);
                            intent.putExtra("user_id" , response.body().getUserID());
                            intent.putExtra("token" , response.body().getToken());
                            intent.putExtra("name" , response.body().getName());
                            activity.startActivity(intent);
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

    public void onClickSignUp(){

    }

    public void onClickOption(){

    }

}
