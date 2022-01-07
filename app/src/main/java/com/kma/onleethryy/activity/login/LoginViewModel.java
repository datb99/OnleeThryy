package com.kma.onleethryy.activity.login;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;
import com.kma.onleethryy.BR;
import com.kma.onleethryy.activity.mainScreen.MainScreenActivity;
import com.kma.onleethryy.activity.register.RegisterActivity;
import com.kma.onleethryy.api.APIClient;
import com.kma.onleethryy.api.APIInterface;
import com.kma.onleethryy.utils.AppUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginViewModel extends BaseObservable {

    private String id , password;
    private ObservableField<String> message = new ObservableField<>();
    private LoginActivity activity;
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    public LoginViewModel(LoginActivity activity){
        this.activity = activity;
        sharedPreferences = activity.getSharedPreferences("config" , Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
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
       startLogin(getId() , getPassword());
    }

    public void onClickSignUp(){
        Intent intent = new Intent(activity , RegisterActivity.class);
        activity.startActivity(intent);
    }

    public void onClickOption(){
//        activity.postImage();
    }

    public void onClickFingerPrint(){
        if (sharedPreferences.getBoolean("fingerPrint" , false)){
            //da duoc setting
            activity.startFingerPrint();
        }else {
            //chua duoc setting
            Toast.makeText(activity , "Vui lòng bật tính năng này trong setting" , Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickPin(){
        if (sharedPreferences.getBoolean("pin" , false)){
            //da duoc setting
            activity.startPINAuthenticate();
        }else {
            //chua duoc setting
            Toast.makeText(activity , "Vui lòng bật tính năng này trong setting" , Toast.LENGTH_SHORT).show();
        }
    }

    public void startLogin(String id , String password){
        APIClient.getClient()
                .create(APIInterface.class)
                .getUserLogin(new APIInterface.postLoginObject(id , password))
                .enqueue(new Callback<APIInterface.returnPostLogin>() {
                    @Override
                    public void onResponse(Call<APIInterface.returnPostLogin> call, Response<APIInterface.returnPostLogin> response) {
                        //post thanh cong
                        if (response.body() != null && response.body().isSuccess()){
                            //dang nhap thanh cong
                            //cap nhat sharedPreferences
                            if (sharedPreferences.contains("id")){
                                editor.remove("id");
                            }
                            if (sharedPreferences.contains("password")){
                                editor.remove("password");
                            }
                            editor.putString("id" , id);
                            editor.putString("password" , password);
                            editor.apply();
                            //luu static string
                            AppUtils.id = id;
                            AppUtils.password = password;
                            AppUtils.idUser = response.body().getUserID();
                            AppUtils.token = response.body().getToken();
                            Intent intent = new Intent(activity , MainScreenActivity.class);
                            intent.putExtra("user_id" , response.body().getUserID());
                            intent.putExtra("token" , response.body().getToken());
                            intent.putExtra("name" , response.body().getName());
                            activity.startActivity(intent);
                            activity.finish();
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

}
