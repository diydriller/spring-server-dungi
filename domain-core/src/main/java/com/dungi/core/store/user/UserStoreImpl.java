package com.dungi.core.store.user;

import com.dungi.common.exception.BaseException;
import com.dungi.core.domain.user.model.User;
import com.dungi.core.domain.user.service.UserStore;
import com.dungi.core.repository.RedisRepository;
import com.dungi.core.repository.user.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.dungi.common.response.BaseResponseStatus.*;

@Component
@RequiredArgsConstructor
public class UserStoreImpl implements UserStore {

    private final UserJpaRepository userJpaRepository;
    private final RedisRepository redisRepository;

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
                .orElseThrow(() -> new BaseException(NOT_EXISTS_EMAIL));
    }


    @Override
    public User findUserById(Long id) {
        return userJpaRepository.findById(id)
                .orElseThrow(() -> new BaseException(NOT_EXIST_USER));
    }

    @Override
    public void saveCode(String number, String code, long time) {
        redisRepository.saveString(number, code, time);
    }

    @Override
    public String getCode(String number) {
        return redisRepository.getString(number)
                .orElseThrow(() -> new BaseException(CODE_NOT_EXIST));
    }

    @Override
    public void saveToken(String token, String email, long time) {
        redisRepository.saveString(token, email, time);
    }

    @Override
    public String getInfo(String token) {
        return redisRepository.getString(token)
                .orElseThrow(() -> new BaseException(AUTHORIZATION_ERROR));
    }
}
