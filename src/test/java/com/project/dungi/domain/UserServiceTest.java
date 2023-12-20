package com.project.dungi.domain;

import com.project.dungi.domain.user.model.User;
import com.project.dungi.domain.user.service.UserServiceImpl;
import com.project.dungi.domain.user.service.UserStore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserStore userStore;

    @Mock
    private PasswordEncoder hashCipher;


    @Test
    @DisplayName("로그인 서비스 성공 테스트")
    void loginSuccessTest() {

        var user = User.builder()
                .email("aaa@naver.com")
                .name("park")
                .nickname("monkey")
                .phoneNumber("01012341234")
                .provider("local")
                .profileImg("http://localhost:9002/static/aaa.jpg")
                .password("encrypted")
                .build();
        given(hashCipher.matches("aaa",user.getPassword()))
                .willReturn(true);
        given(userStore.findUserByEmail("aaa@naver.com"))
                .willReturn(user);

        var loginUser = userService.login("aaa@naver.com","aaa");

        assertThat(loginUser).isEqualTo(user);
    }
}
