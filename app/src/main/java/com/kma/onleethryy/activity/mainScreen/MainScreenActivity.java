package com.kma.onleethryy.activity.mainScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayoutMediator;
import com.kma.onleethryy.R;
import com.kma.onleethryy.adapter.FragmentAdapter;
import com.kma.onleethryy.databinding.ActivityMainScreenBinding;

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

        binding.textView.setText(this.getIntent().getStringExtra("name"));

        FragmentAdapter adapter = new FragmentAdapter(this);
        binding.pager.setAdapter(adapter);
        new TabLayoutMediator(binding.tabLayout, binding.pager, (tab , position ) -> {
            switch (position){
                case 0:
                    tab.setText("All User")
                            .setIcon(AppCompatResources.getDrawable(this, R.drawable.ic_baseline_supervisor_account_24));
                    break;
                case 1:
                    tab.setText("Chats")
                            .setIcon(AppCompatResources.getDrawable(this , R.drawable.ic_baseline_chat_24));
                    break;
                case 2:
                    tab.setText("Setting")
                            .setIcon(AppCompatResources.getDrawable(this , R.drawable.ic_baseline_settings_24));
                    break;
            }
        }).attach();
    }
}