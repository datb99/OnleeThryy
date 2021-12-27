package com.kma.onleethryy.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.kma.onleethryy.fragment.allUser.AllUserFragment;
import com.kma.onleethryy.fragment.friend.FriendFragment;
import com.kma.onleethryy.fragment.listChat.ListChatFragment;

public class FragmentAdapter extends FragmentStatePagerAdapter  {


    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new AllUserFragment();
                break;
            case 1:
                fragment = new FriendFragment();
                break;
            case 2:
                fragment = new ListChatFragment();
                break;
        }


        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "All Users";
                break;
            case 1:
                title = "All Friends";
                break;
            case 2:
                title = "All Chats";
                break;
        }
        return title;
    }

}
