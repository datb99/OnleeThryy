package com.kma.onleethryy.api;

import com.google.gson.annotations.SerializedName;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface APIInterface {

    @POST("api/users/login")
    Call<returnPostLogin> getUserLogin(@Body postLoginObject user);

    @GET("api/users")
    Call<List<returnAllUsers>> getAllUsers(@Header("Authorization") String content_type);

    class postLoginObject{
        String username;
        String password;

        public postLoginObject(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    class returnPostLogin{
        @SerializedName("success")
        boolean success;
        @SerializedName("token")
        String token;
        @SerializedName("name")
        String name;
        @SerializedName("username")
        String username;
        @SerializedName("userId")
        String userID;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }
    }

    class returnAllUsers{
        @SerializedName("_id")
        String id;
        @SerializedName("name")
        String name;
        @SerializedName("username")
        String username;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }



}




