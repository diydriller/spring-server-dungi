package com.dungi.core.infrastructure.store.user;

import com.dungi.common.exception.BaseException;
import com.dungi.core.domain.user.model.User;
import com.dungi.core.domain.user.service.UserStore;
import com.dungi.core.infrastructure.jpa.user.UserJpaRepository;
import com.dungi.core.infrastructure.redis.RedisRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.dungi.common.response.BaseResponseStatus.*;

@Component
@RequiredArgsConstructor
public class UserStoreImpl implements UserStore {

    @Value("${twilio.accountId}")
    private String twilioAccountSid;
    @Value("${twilio.authToken}")
    private String twilioAuthToken;
    @Value("${twilio.adminPhoneNumber}")
    private String adminPhoneNumber;

    private final UserJpaRepository userJpaRepository;
    private final RedisRepository redisRepository;

    @Override
    public void sendSms(String phoneNumber,String randomNumber) {
        Twilio.init(twilioAccountSid, twilioAuthToken);
        Message.creator(
                        new PhoneNumber(phoneNumber),
                        new PhoneNumber(adminPhoneNumber),
                        "enter the number : " + randomNumber)
                .create();
    }

    @Override
    public void saveUser(User user) {
        userJpaRepository.save(user);
    }

    @Override
    public void checkEmailPresent(String email) {
        userJpaRepository.checkUserByEmail(email)
                .ifPresent(m -> {
                    throw new BaseException(ALREADY_EXISTS_EMAIL);
                });
    }

    @Override
    public User findUserByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .orElseThrow(()-> new BaseException(NOT_EXISTS_EMAIL));
    }

    @Override
    public void saveCode(String number, String code) {
        redisRepository.saveString(number,code,90);
    }

    @Override
    public String getCode(String number) {
        return redisRepository.getString(number)
                .orElseThrow(() -> new BaseException(CODE_NOT_EXIST));
    }

    @Override
    public User findUserById(Long id) {
        return userJpaRepository.findById(id)
                .orElseThrow(() -> new BaseException(NOT_EXIST_USER));
    }
}
