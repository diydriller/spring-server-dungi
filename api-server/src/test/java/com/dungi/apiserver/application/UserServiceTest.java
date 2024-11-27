package com.dungi.apiserver.application;

import com.dungi.apiserver.application.user.service.UserService;
import com.dungi.common.value.Provider;
import com.dungi.core.domain.user.model.User;
import com.dungi.core.integration.store.user.UserStore;
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
    private UserService userService;

    @Mock
    private UserStore userStore;

    @Mock
    private PasswordEncoder hashCipher;


    @Test
    @DisplayName("로그인 서비스 성공 테스트")
    void loginSuccessTest() {

        // given
        var user = User.builder()
                .email("aaa@naver.com")
                .name("park")
                .nickname("monkey")
                .phoneNumber("01012341234")
                .provider(Provider.LOCAL)
                .profileImg("http://localhost:9002/static/aaa.jpg")
                .password("encrypted")
                .build();

        given(hashCipher.matches("aaa", user.getPassword()))
                .willReturn(true);
        given(userStore.getUserByEmail("aaa@naver.com"))
                .willReturn(user);

        // when
        var loginUser = userService.login("aaa@naver.com", "aaa");

        // then
        assertThat(loginUser).isEqualTo(user);
    }
}
