package com.dungi.apiserver.presentation;

import com.dungi.apiserver.application.user.service.UserService;
import com.dungi.apiserver.presentation.user.controller.UserController;
import com.dungi.apiserver.presentation.user.dto.JoinRequestDto;
import com.dungi.apiserver.presentation.user.dto.LoginRequestDto;
import com.dungi.apiserver.presentation.user.dto.TokenResponseDto;
import com.dungi.apiserver.web.TokenProvider;
import com.dungi.common.value.SnsProvider;
import com.dungi.core.domain.user.model.User;
import com.dungi.core.integration.store.user.UserStore;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private TokenProvider tokenProvider;

    @Mock
    private UserStore userStore;

    private MockMvc mockMvc;

    @BeforeEach
    public void initEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    @DisplayName("회원가입 컨트롤러 성공 테스트")
    void signUpSuccessTest() throws Exception {
        // given
        final var requestDto = new JoinRequestDto(
                "aaa@naver.com",
                "aaa",
                "park",
                "01012341234",
                "park",
                new MockMultipartFile(
                        "img", "test.jpg", "image/jpg", "test".getBytes()
                )
        );

        // when
        var result = mockMvc.perform(multipart("/user")
                .file((MockMultipartFile) requestDto.getImg())
                .param("email", requestDto.getEmail())
                .param("password", requestDto.getPassword())
                .param("nickname", requestDto.getNickname())
                .param("name", requestDto.getName())
                .param("phoneNumber", requestDto.getPhoneNumber())
                .contentType(MediaType.MULTIPART_FORM_DATA)
        );

        // then
        result.andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인 컨트롤러 성공 테스트")
    void loginSuccessTest() throws Exception {
        // given
        final var requestDto = new LoginRequestDto("aaa@naver.com", "aaa");
        var user = User.builder()
                .email("aaa@naver.com")
                .name("park")
                .nickname("monkey")
                .phoneNumber("01012341234")
                .snsProvider(SnsProvider.LOCAL)
                .profileImg("http://localhost:9002/static/aaa.jpg")
                .password("encrypted")
                .build();
        Field field = User.class.getDeclaredField("id");
        field.setAccessible(true);
        field.set(user, 1L);

        given(userService.login(requestDto.getEmail(), requestDto.getPassword()))
                .willReturn(user);
        given(tokenProvider.createAccessToken(user.getEmail()))
                .willReturn("accessToken");
        given(tokenProvider.createRefreshToken())
                .willReturn("refreshToken");
        given(tokenProvider.getExpirationDuration("refreshToken"))
                .willReturn(10000L);
        willDoNothing().given(userStore).saveToken("refreshToken", user.getEmail(), 10000L);

        // when
        var tokenDto = (TokenResponseDto) userController.login(requestDto, new MockHttpSession()).getData();

        var result = mockMvc.perform(
                MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(requestDto))
        );

        // then
        result.andExpect(status().isOk());
        assertThat(tokenDto.getAccessToken()).isEqualTo("accessToken");
        assertThat(tokenDto.getRefreshToken()).isEqualTo("refreshToken");
    }
}
