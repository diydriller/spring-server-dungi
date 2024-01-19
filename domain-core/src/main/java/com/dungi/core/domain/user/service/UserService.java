package com.dungi.core.domain.user.service;

import com.dungi.core.domain.user.dto.SnsTokenDto;
import com.dungi.core.domain.user.model.User;

public interface UserService {
    void createUser(
            String email,
            byte[] imageContent,
            String imageName,
            String password,
            String name,
            String nickname,
            String phoneNumber
    ) throws Exception;
    void checkEmailPresent(String email) throws Exception;
    void sendSms(String phoneNumber) throws Exception;
    void compareCode(String code, String phoneNumber) throws Exception;
    void createSnsUser(
            String email,
            String nickname,
            String kakaoImg,
            String accessToken,
            byte[] imageContent,
            String imageName
    ) throws Exception;
    SnsTokenDto snsToken(String code) throws Exception;
    User login(String email, String password) throws Exception;
    User snsLogin(String email, String accessToken) throws Exception;
}
