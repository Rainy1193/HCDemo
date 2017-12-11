package com.homecaravan.android.handling;
import android.util.Patterns;

import java.util.regex.Pattern;

public class ValidateData {
    public static boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }

    }

    public static boolean isName(String name) {
        if (Pattern.compile("[a-zA-Z\\s]*").matcher(name).matches()) {
            return true;
        }
        return false;
    }

    public static boolean isPhone(String phone) {
        if (phone == null) {
            return false;
        } else {
            return Patterns.PHONE.matcher(phone).matches();
        }
    }

    public static boolean isZipcode(String zipcode) {
        if (Pattern.compile("\\G\\d+\\.?\\d*", Pattern.CASE_INSENSITIVE).matcher(zipcode).matches()) {
            return true;
        }
        return false;
    }


    public static boolean isPassword(String password) {
        if (password.trim().length() >= 6) {
            return true;
        }
        return false;
    }

    public static boolean isCode(String code) {
        return Pattern.compile("[a-zA-Z0-9]*", Pattern.CASE_INSENSITIVE).matcher(code).matches() && code.length() == 5;
    }

    public static boolean isEmpty(String data) {
        if (data.length() == 0) {
            return true;
        }
        return false;
    }
}
