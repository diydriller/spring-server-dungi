package com.project.dungi.infrastructure;

import com.project.dungi.domain.user.model.User;
import com.project.dungi.infrastructure.jpa.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("유저 저장 성공 테스트")
    void saveUserSuccessTest() {
        var user = User.builder()
                .email("aaa@naver.com")
                .name("park")
                .nickname("monkey")
                .phoneNumber("01012341234")
                .provider("local")
                .profileImg("http://localhost:9002/static/aaa.jpg")
                .password("encrypted")
                .build();
        userRepository.save(user);

        var savedUser = userRepository.findByEmail(user.getEmail()).get();

        assertEquals(user.getId(), savedUser.getId());
    }
}
