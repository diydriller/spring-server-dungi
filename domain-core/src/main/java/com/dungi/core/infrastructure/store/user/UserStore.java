package com.dungi.core.infrastructure.store.user;

import com.dungi.core.domain.user.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStore {
    void saveUser(User user);

    void checkEmailPresent(String email);

    User findUserByEmail(String email);

    User findUserById(Long id);
}
