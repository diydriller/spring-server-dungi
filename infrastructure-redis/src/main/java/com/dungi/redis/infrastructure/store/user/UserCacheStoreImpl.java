package com.dungi.redis.infrastructure.store.user;

import com.dungi.common.exception.BaseException;
import com.dungi.core.domain.user.service.UserCacheStore;
import com.dungi.redis.infrastructure.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.dungi.common.response.BaseResponseStatus.CODE_NOT_EXIST;

@Component
@RequiredArgsConstructor
public class UserCacheStoreImpl implements UserCacheStore {

    private final RedisRepository redisRepository;

    @Override
    public void saveCode(String number, String code) {
        redisRepository.saveString(number,code,90);
    }

    @Override
    public String getCode(String number) {
        return redisRepository.getString(number)
                .orElseThrow(() -> new BaseException(CODE_NOT_EXIST));
    }
}
