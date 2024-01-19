package com.dungi.core.domain.sms;

public interface SmsSender {
    void sendSms(String phoneNumber, String content);
}
