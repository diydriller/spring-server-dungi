package com.dungi.jpa.infrastructure.store.user;

import com.dungi.common.exception.BaseException;
import com.dungi.core.domain.user.model.User;
import com.dungi.core.domain.user.service.UserStore;
import com.dungi.jpa.infrastructure.repository.user.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.dungi.common.response.BaseResponseStatus.*;

@Component
@RequiredArgsConstructor
public class UserStoreImpl implements UserStore {

    private final UserJpaRepository userJpaRepository;

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
    public User findUserById(Long id) {
        return userJpaRepository.findById(id)
                .orElseThrow(() -> new BaseException(NOT_EXIST_USER));
    }
}
