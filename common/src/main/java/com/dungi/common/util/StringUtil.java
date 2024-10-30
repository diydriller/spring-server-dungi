package com.dungi.common.util;

import java.util.UUID;

public class StringUtil {

    public static final String LOGIN_USER = "login_user";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String REQUEST_KEY = "request-id";
    public static final String VOTE_TYPE = "V";
    public static final String NOTICE_TYPE = "N";

    public static String trimPhoneNumber(String phoneNumber){
        return "+82"+phoneNumber.substring(1);
    }

    public static String randomNumber(){
        return String.valueOf(Math.random() * 9000+1000);
    }

    public static String randomString() {
        return UUID.randomUUID().toString();
    }
}
