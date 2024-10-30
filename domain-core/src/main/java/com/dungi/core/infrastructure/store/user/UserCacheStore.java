package com.dungi.core.infrastructure.store.user;

import org.springframework.stereotype.Repository;

@Repository
public interface UserCacheStore {
    void saveCode(String number, String code, long time);

    String getCode(String number);

    void saveToken(String token, String email, long time);

    String getInfo(String token);
}
