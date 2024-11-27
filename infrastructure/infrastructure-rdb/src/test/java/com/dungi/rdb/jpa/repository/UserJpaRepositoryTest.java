package com.dungi.rdb.jpa.repository;

import com.dungi.common.exception.BaseException;
import com.dungi.common.value.Provider;
import com.dungi.core.domain.user.model.User;
import com.dungi.rdb.jpa.repository.user.UserJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.dungi.common.response.BaseResponseStatus.NOT_EXIST_USER;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class UserJpaRepositoryTest {
    @Autowired
    private UserJpaRepository userJpaRepository;

    @Test
    @DisplayName("유저 저장 성공 테스트")
    void saveUserSuccessTest() {

        // given
        var user = createUser();

        // when
        userJpaRepository.save(user);
        var savedUser = findUser(user.getEmail());

        // then
        assertEquals(user.getId(), savedUser.getId());
    }

    private User createUser() {
        return User.builder()
                .email("aaa@naver.com")
                .name("park")
                .nickname("monkey")
                .phoneNumber("01012341234")
                .provider(Provider.LOCAL)
                .profileImg("http://localhost:9002/static/aaa.jpg")
                .password("encrypted")
                .build();
    }

    private User findUser(String email) {
        return userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(NOT_EXIST_USER));
    }
}
