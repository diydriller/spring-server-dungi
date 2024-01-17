package com.project.dungi.domain.user.service;


import com.project.dungi.domain.user.model.User;

public interface UserStore {
    void sendSms(String phoneNumber, String randomNumber);
    void saveUser(User user);
    void checkEmailPresent(String email);
    User findUserByEmail(String email);
    void saveCode(String number, String code);
    String getCode(String number);
    User findUserById(Long id);
}
