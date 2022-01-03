package com.kma.onleethryy.utils;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.kma.onleethryy.api.APIInterface;

public class AppUtils {

    public static String idUser = "";
    public static String token = "";


    public static void hideKeyBoard(Activity activity){
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
