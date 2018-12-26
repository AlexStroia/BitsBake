package co.alexdev.bitsbake.model.model;

import android.text.TextUtils;

public class Validator {

    public static String isFieldValid(String field) {
        String msg = "";
        if (!TextUtils.isEmpty(field)) {
            msg = field;
        }
        return msg;
    }
}
