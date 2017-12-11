package com.homecaravan.android.models;

/**
 * Created by RAINY on 4/1/2016.
 */
public class Country {
    private String name;
    private String dialCode;
    private String code;

    public Country(String name, String dialCode, String code) {
        this.name = name;
        this.dialCode = dialCode;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDialCode() {
        return dialCode;
    }

    public void setDialCode(String dialCode) {
        this.dialCode = dialCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return name + dialCode + code;
    }



}
