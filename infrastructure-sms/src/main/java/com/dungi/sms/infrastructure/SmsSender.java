package com.dungi.sms.infrastructure;

public interface SmsSender {
    void sendSms(String phoneNumber, String content);
}
