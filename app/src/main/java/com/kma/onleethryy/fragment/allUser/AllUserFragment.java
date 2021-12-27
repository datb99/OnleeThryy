package com.kma.onleethryy.fragment.allUser;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kma.onleethryy.R;
import com.kma.onleethryy.activity.mainScreen.MainScreenViewModel;
import com.kma.onleethryy.databinding.FragmentAllUserBinding;


public class AllUserFragment extends Fragment {

    FragmentAllUserBinding binding;
    AllUserFragmentViewModel viewModel;

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
}