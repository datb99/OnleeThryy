package com.kma.onleethryy.fragment.setting;

import static android.content.Context.KEYGUARD_SERVICE;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.biometric.BiometricManager;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;
import com.kma.onleethryy.R;
import com.kma.onleethryy.databinding.FragmentSettingBinding;
import com.kma.onleethryy.utils.AppUtils;

import java.util.Objects;


public class SettingFragment extends Fragment {

    FragmentSettingBinding binding;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = requireContext()
                .getSharedPreferences("config" , Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_setting,
                container,
                false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //kiem tra config
        binding.fingerPrint.setChecked(sharedPreferences.getBoolean("fingerPrint" , false));
        binding.pin.setChecked(sharedPreferences.getBoolean("pin" , false));


        binding.fingerPrint.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onChangeFingerPrintOption();
            }
        });

        binding.pin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onChangePinOption();
            }
        });
    }

    private void onChangeFingerPrintOption(){
        BiometricManager biometricManager = androidx.biometric.BiometricManager.from(requireContext());

        switch (biometricManager.canAuthenticate(BIOMETRIC_WEAK)) {

            case BiometricManager.BIOMETRIC_SUCCESS:
//                Toast.makeText(getApplicationContext() , "quet van tay" , Toast.LENGTH_LONG).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(getContext() , "Thi???t b??? n??y kh??ng c?? c???m bi???n v??n tay" , Toast.LENGTH_LONG).show();
                binding.fingerPrint.setChecked(false);
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(getContext() , "C???m bi???n v??n tay hi???n kh??ng kh??? d???ng" , Toast.LENGTH_LONG).show();
                binding.fingerPrint.setChecked(false);
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Toast.makeText(getContext() , "C???m bi???n v??n tay ch??a ???????c c??i ?????t" , Toast.LENGTH_LONG).show();
                binding.fingerPrint.setChecked(false);
                break;
        }
        setFingerPrint();
    }

    private void setFingerPrint(){
        if (sharedPreferences.contains("fingerPrint")){
            editor.remove("fingerPrint");
        }
        editor.putBoolean("fingerPrint" , binding.fingerPrint.isChecked());
        editor.apply();
    }

    private void onChangePinOption(){
        KeyguardManager km = (KeyguardManager)getActivity().getSystemService(KEYGUARD_SERVICE);
        if (!km.isKeyguardSecure()){
            Toast.makeText(getContext() , "Thi???t b??? n??y ch??a ???????c c??i ?????t m?? PIN" , Toast.LENGTH_LONG).show();
            binding.pin.setChecked(false);
        }
        if (sharedPreferences.contains("pin")){
            editor. remove("pin");
        }
        editor.putBoolean("pin" , binding.pin.isChecked());
        editor.apply();

    }
}