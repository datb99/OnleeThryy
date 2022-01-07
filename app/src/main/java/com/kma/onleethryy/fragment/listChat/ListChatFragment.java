package com.kma.onleethryy.fragment.listChat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kma.onleethryy.R;
import com.kma.onleethryy.adapter.ListChatAdapter;
import com.kma.onleethryy.api.APIInterface;
import com.kma.onleethryy.databinding.FragmentListChatBinding;
import com.kma.onleethryy.fragment.allUser.AllUserFragmentViewModel;

import java.util.List;


public class ListChatFragment extends Fragment {

    FragmentListChatBinding binding;
    ListChatFragmentViewModel viewModel;
    ListChatAdapter adapter;

    public ListChatFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_list_chat,
                container,
                false);
        //set view model
        viewModel = new ListChatFragmentViewModel(this);
        binding.setLifecycleOwner(getActivity());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.getAllConversation();
    }

    public void initListChat(List<APIInterface.returnConversation> list){
        adapter = new ListChatAdapter(getContext() , list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);
    }
}