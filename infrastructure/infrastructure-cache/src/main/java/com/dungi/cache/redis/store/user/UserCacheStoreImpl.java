package com.dungi.cache.redis.store.user;

import com.dungi.common.exception.BaseException;
import com.dungi.cache.redis.repository.RedisRepository;
import com.dungi.core.infrastructure.store.user.UserCacheStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.dungi.common.response.BaseResponseStatus.AUTHORIZATION_ERROR;
import static com.dungi.common.response.BaseResponseStatus.CODE_NOT_EXIST;

@RequiredArgsConstructor
@Component
public class UserCacheStoreImpl implements UserCacheStore {
    private final RedisRepository redisRepository;

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
