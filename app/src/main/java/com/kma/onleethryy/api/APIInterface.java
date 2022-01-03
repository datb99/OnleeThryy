package com.kma.onleethryy.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {

    @POST("api/users/login")
    Call<returnPostLogin> getUserLogin(@Body postLoginObject user);

    @GET("api/users")
    Call<List<returnAllUsers>> getAllUsers(@Header("Authorization") String content_type);

    @GET("api/messages/conversations")
    Call<List<returnConversation>> getAllConversation(@Header("Authorization") String content_type);

    @GET("api/messages/conversations/query")
    Call<List<returnMessage>> getAllChat(@Header("Authorization") String content_type , @Query("userId" ) String userId);

    @POST("api/messages/")
    Call<returnSendMessage> sendMessage(@Header("Authorization") String content_type , @Body sendMessageObject message);

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
        @SerializedName("avatar")
        String avatar;

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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }

    class returnConversation{
        @SerializedName("_id")
        String id;
        @SerializedName("__v")
        int v;
        @SerializedName("date")
        String date;
        @SerializedName("lastMessage")
        String lastMessage;
        @SerializedName("recipients")
        List<String> recipients;
        @SerializedName("recipientObj")
        List<recipientObj> recipientObj;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getV() {
            return v;
        }

        public void setV(int v) {
            this.v = v;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getLastMessage() {
            return lastMessage;
        }

        public void setLastMessage(String lastMessage) {
            this.lastMessage = lastMessage;
        }

        public List<String> getRecipients() {
            return recipients;
        }

        public void setRecipients(List<String> recipients) {
            this.recipients = recipients;
        }

        public List<APIInterface.recipientObj> getRecipientObj() {
            return recipientObj;
        }

        public void setRecipientObj(List<APIInterface.recipientObj> recipientObj) {
            this.recipientObj = recipientObj;
        }
    }

    class recipientObj{
        @SerializedName("_id")
        String id;
        @SerializedName("name")
        String name;
        @SerializedName("username")
        String userName;

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

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }

    class returnMessage{
        @SerializedName("_id")
        String id;
        @SerializedName("conversation")
        String conversationId;
        @SerializedName("to")
        String toId;
        @SerializedName("from")
        String fromId;
        @SerializedName("body")
        String body;
        @SerializedName("date")
        String date;
        @SerializedName("__v")
        int v;
        @SerializedName("toObj")
        List<recipientObj> toObject;
        @SerializedName("fromObj")
        List<recipientObj> fromObject;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getConversationId() {
            return conversationId;
        }

        public void setConversationId(String conversationId) {
            this.conversationId = conversationId;
        }

        public String getToId() {
            return toId;
        }

        public void setToId(String toId) {
            this.toId = toId;
        }

        public String getFromId() {
            return fromId;
        }

        public void setFromId(String fromId) {
            this.fromId = fromId;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getV() {
            return v;
        }

        public void setV(int v) {
            this.v = v;
        }

        public List<recipientObj> getToObject() {
            return toObject;
        }

        public void setToObject(List<recipientObj> toObject) {
            this.toObject = toObject;
        }

        public List<recipientObj> getFromObject() {
            return fromObject;
        }

        public void setFromObject(List<recipientObj> fromObject) {
            this.fromObject = fromObject;
        }
    }

    class returnSendMessage{
        @SerializedName("message")
        String message;
        @SerializedName("conversationId")
        String conversationID;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getConversationID() {
            return conversationID;
        }

        public void setConversationID(String conversationID) {
            this.conversationID = conversationID;
        }
    }

    class sendMessageObject{
        String body;
        String to;

        public sendMessageObject(String body , String to){
            this.body = body;
            this.to = to;
        }
    }
}




