package com.dungi.core.integration.store.user;

import com.dungi.core.domain.user.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStore {
    void saveUser(User user);

    void checkEmailPresent(String email);

    User findUserByEmail(String email);

    User findUserById(Long id);
}
