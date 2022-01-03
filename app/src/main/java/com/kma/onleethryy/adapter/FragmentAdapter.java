package com.kma.onleethryy.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.kma.onleethryy.fragment.allUser.AllUserFragment;
import com.kma.onleethryy.fragment.friend.SettingFragment;
import com.kma.onleethryy.fragment.listChat.ListChatFragment;

public class FragmentAdapter extends FragmentStateAdapter {


    public FragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position){
            case 1:
                fragment = new ListChatFragment();
                break;
            case 2:
                fragment = new SettingFragment();
                break;
            default:
                fragment = new AllUserFragment();
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
