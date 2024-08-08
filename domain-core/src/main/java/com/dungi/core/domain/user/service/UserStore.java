package com.dungi.core.domain.user.service;

import com.dungi.core.domain.user.model.User;

public interface UserStore {
    void saveUser(User user);

    void checkEmailPresent(String email);

    User findUserByEmail(String email);

    User findUserById(Long id);

    void saveCode(String number, String code, long time);

    String getCode(String number);

    void saveToken(String token, String email, long time);
}
