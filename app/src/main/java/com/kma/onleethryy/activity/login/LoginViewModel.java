package com.kma.onleethryy.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kma.onleethryy.BR;
import com.kma.onleethryy.activity.mainScreen.MainScreenActivity;


public class LoginViewModel extends BaseObservable {



    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("User");
    private String id , password;
    private ObservableField<String> message = new ObservableField<>();
    public ObservableField<Boolean> isLoginSuccess = new ObservableField<>();
    LoginActivity activity;

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
        checkLogin(getId() , getPassword());
    }

    public void onClickSignUp(){

    }

    public void onClickOption(){

    }

    private void checkLogin(String id, String password){
        final boolean[] check = {false};
        databaseReference.orderByChild("id").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()){
                    String pass = child.child("password").getValue().toString();
                    if (pass.equals(password)){
                        onFinishCheckLogin(true);
                        return;
                    }
                }
                onFinishCheckLogin(false);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void onFinishCheckLogin(boolean result){
        if (result){
            //login thanh cong
            Log.d("TAG1432", "onClickLogin: success");
            Intent intent = new Intent(activity , MainScreenActivity.class);
            intent.putExtra("User_ID", getId());
            activity.startActivity(intent);
        }else {
            //login fail
            Log.d("TAG1432", "onClickLogin: fail");
        }
    }
}
