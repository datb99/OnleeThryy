package com.kma.onleethryy.activity.login;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.kma.onleethryy.R;
import com.kma.onleethryy.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this , R.layout.activity_login);
        LoginViewModel viewModel = new LoginViewModel(this);
        binding.setViewModel(viewModel);


    }

}
