package com.kma.onleethryy.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.kma.onleethryy.api.APIInterface;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AppUtils {

    public static String idUser = "";
    public static String token = "";
    public static APIInterface.returnAllUsers user;
    public static String id = "";
    public static String password = "";


    public static void hideKeyBoard(Activity activity){
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String hashString(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString().substring(0 , 16);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getAlias(Context context){
        return AppUtils.hashString(context.getPackageName());
    }

    public static void setUser(APIInterface.returnAllUsers user) {
        AppUtils.user = user;
    }
}
