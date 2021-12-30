package com.kma.onleethryy.fragment.allUser;

import android.util.Log;

import androidx.databinding.BaseObservable;

import com.google.gson.Gson;
import com.kma.onleethryy.activity.mainScreen.MainScreenActivity;
import com.kma.onleethryy.api.APIClient;
import com.kma.onleethryy.api.APIInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllUserFragmentViewModel extends BaseObservable {

    AllUserFragment fragment;
    ArrayList<APIInterface.returnAllUsers> listUser = new ArrayList<>();

    public AllUserFragmentViewModel (AllUserFragment fragment){
        this.fragment = fragment;
    }

    public void getAllUser(){
        APIClient.getClient()
                .create(APIInterface.class)
                .getAllUsers(((MainScreenActivity)fragment.getActivity()).getIntent().getStringExtra("token"))
                .enqueue(new Callback<List<APIInterface.returnAllUsers>>() {
                    @Override
                    public void onResponse(Call<List<APIInterface.returnAllUsers>> call, Response<List<APIInterface.returnAllUsers>> response) {
                        Log.d("TAG1432", "onResponse: success " + new Gson().toJson(response.body()));
                    }

                    @Override
                    public void onFailure(Call<List<APIInterface.returnAllUsers>> call, Throwable t) {

                    }
                });

    }




}
