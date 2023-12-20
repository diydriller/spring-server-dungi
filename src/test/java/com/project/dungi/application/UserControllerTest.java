package com.project.dungi.application;

import com.google.gson.Gson;
import com.project.dungi.application.user.controller.UserController;
import com.project.dungi.application.user.dto.JoinRequestDto;
import com.project.dungi.application.user.dto.LoginRequestDto;
import com.project.dungi.domain.user.model.User;
import com.project.dungi.domain.user.service.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    @BeforeEach
    public void initEach(){
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    @DisplayName("회원가입 컨트롤러 성공 테스트")
    void signUpSuccessTest() throws Exception {

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

        mockMvc.perform(multipart("/user")
                .file((MockMultipartFile) requestDto.getImg())
                .param("email", requestDto.getEmail())
                .param("password", requestDto.getPassword())
                .param("nickname", requestDto.getNickname())
                .param("name", requestDto.getName())
                .param("phoneNumber", requestDto.getPhoneNumber())
                .contentType(MediaType.MULTIPART_FORM_DATA)
        ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인 컨트롤러 성공 테스트")
    void loginSuccessTest() throws Exception {

        final var requestDto = new LoginRequestDto("aaa@naver.com","aaa");
        var user = User.builder()
                .email("aaa@naver.com")
                .name("park")
                .nickname("monkey")
                .phoneNumber("01012341234")
                .provider("local")
                .profileImg("http://localhost:9002/static/aaa.jpg")
                .password("encrypted")
                .build();
        when(userService.login(requestDto.getEmail(), requestDto.getPassword()))
                .thenReturn(user);

        final var resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(requestDto))
        );

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("data").isString());
    }
}
