package com.kma.onleethryy.fragment.allUser;


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
import com.kma.onleethryy.adapter.AllUsersAdapter;
import com.kma.onleethryy.api.APIInterface;
import com.kma.onleethryy.databinding.FragmentAllUserBinding;

import java.util.List;


public class AllUserFragment extends Fragment {

    FragmentAllUserBinding binding;
    AllUserFragmentViewModel viewModel;
    AllUsersAdapter adapter;

    public AllUserFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //bind view
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_all_user,
                container,
                false);
        //set view model
        viewModel = new AllUserFragmentViewModel(this);
        binding.setViewModel(viewModel);
        //add lifecycle
        binding.setLifecycleOwner(getActivity());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getAllUser();
    }

    public void initListUer(List<APIInterface.returnAllUsers> listUser){
        adapter = new AllUsersAdapter(getContext() , listUser);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);
    }
}