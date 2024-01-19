package com.dungi.core.domain.user;

import com.dungi.core.domain.user.model.User;

public interface UserStore {
    void saveUser(User user);
    void checkEmailPresent(String email);
    User findUserByEmail(String email);
    User findUserById(Long id);
}
