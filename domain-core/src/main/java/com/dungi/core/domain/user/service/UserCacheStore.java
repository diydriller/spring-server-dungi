package com.dungi.core.domain.user.service;

public interface UserCacheStore {
    void saveCode(String number, String code);
    String getCode(String number);
}
