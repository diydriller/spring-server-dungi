package com.dungi.core.domain.user;

public interface UserCacheStore {
    void saveCode(String number, String code);
    String getCode(String number);
}
