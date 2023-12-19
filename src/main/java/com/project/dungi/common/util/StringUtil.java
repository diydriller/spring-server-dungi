package com.project.dungi.common.util;

public class StringUtil {

    public static final String LOGIN_USER = "login_user";
    public static final String ACCESS_TOKEN = "access_token";

    public static String trimPhoneNumber(String phoneNumber){
        return "+82"+phoneNumber.substring(1);
    }

    public static String randomNumber(){
        return String.valueOf(Math.random() * 9000+1000);
    }
}
