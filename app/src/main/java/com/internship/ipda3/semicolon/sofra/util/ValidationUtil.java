package com.internship.ipda3.semicolon.sofra.util;

import android.text.TextUtils;

public class ValidationUtil {

    public static boolean isEmpty(String text){
        return TextUtils.isEmpty(text);
    }

    public static boolean validatePassword(String password){
        return password.length() > 3;
    }

}
