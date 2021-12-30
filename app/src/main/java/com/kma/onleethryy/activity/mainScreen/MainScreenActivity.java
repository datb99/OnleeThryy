package com.kma.onleethryy.activity.mainScreen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.kma.onleethryy.R;
import com.kma.onleethryy.adapter.FragmentAdapter;
import com.kma.onleethryy.databinding.ActivityMainScreenBinding;

import java.util.Objects;

public class MainScreenActivity extends AppCompatActivity {

    ActivityMainScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this , R.layout.activity_main_screen);
        MainScreenViewModel viewModel = new MainScreenViewModel(this);
        binding.setViewModel(viewModel);

        addControl();

    }

    private void addControl(){
        FragmentAdapter adapter = new FragmentAdapter(this);
        binding.pager.setAdapter(adapter);
        new TabLayoutMediator(binding.tabLayout, binding.pager, (tab , position ) -> {
            switch (position){
                case 0:
                    tab.setText("All User")
                            .setIcon(AppCompatResources.getDrawable(this, R.drawable.ic_baseline_supervisor_account_24));
                    break;
                case 1:
                    tab.setText("Friends")
                            .setIcon(AppCompatResources.getDrawable(this , R.drawable.ic_baseline_person_24));
                    break;
                case 2:
                    tab.setText("Chats")
                            .setIcon(AppCompatResources.getDrawable(this , R.drawable.ic_baseline_chat_24));
                    break;
            }
        }).attach();
//        for (int i = 0 ; i < binding.tabLayout.getTabCount() ; i ++){
//            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) Objects.requireNonNull(binding.tabLayout.getTabAt(i)).view.getChildAt(0).getLayoutParams();
//            params.bottomMargin = 0;
//            Objects.requireNonNull(binding.tabLayout.getTabAt(i)).view.getChildAt(0).setLayoutParams(params);
//        }
    }
}