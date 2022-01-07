package com.kma.onleethryy.fragment.listChat;

import androidx.databinding.BaseObservable;
import com.kma.onleethryy.activity.mainScreen.MainScreenActivity;
import com.kma.onleethryy.api.APIClient;
import com.kma.onleethryy.api.APIInterface;
import com.kma.onleethryy.utils.AppUtils;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListChatFragmentViewModel extends BaseObservable {

    ListChatFragment fragment;


    public ListChatFragmentViewModel(ListChatFragment fragment){
        this.fragment = fragment;
    }

    public List<APIInterface.returnConversation> getAllConversation(){

        APIClient.getClient()
                .create(APIInterface.class)
                .getAllConversation(((MainScreenActivity)fragment.getActivity()).getIntent().getStringExtra("token"))
                .enqueue(new Callback<List<APIInterface.returnConversation>>() {
                    @Override
                    public void onResponse(Call<List<APIInterface.returnConversation>> call, Response<List<APIInterface.returnConversation>> response) {
                        initListChat(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<APIInterface.returnConversation>> call, Throwable t) {
                    }
                });

        return null;
    }

    private void initListChat(List<APIInterface.returnConversation> listChat){
        List<APIInterface.returnConversation> listChatOfUser = new ArrayList<>();
        for (int i = 0 ; i < listChat.size() ; i++){
            boolean checkNull = false;
            boolean checkEqual = false;
            for (int j = 0 ; j < listChat.get(i).getRecipients().size() ; j++){
                if (listChat.get(i).getRecipients().get(j) != null){
                    if (listChat.get(i).getRecipients().get(j).contains(AppUtils.idUser)){
                        checkEqual = true;
                    }
                }else {
                    checkNull = true;
                }
            }
            if (!checkNull && checkEqual){
                listChatOfUser.add(listChat.get(i));
            }
        }
        fragment.initListChat(listChatOfUser);
    }


    


}
