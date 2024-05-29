package com.dungi.apiserver.application.user.controller;

import com.dungi.apiserver.application.user.dto.*;
import com.dungi.apiserver.web.TokenProvider;
import com.dungi.common.response.BaseResponse;
import com.dungi.core.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.dungi.common.response.BaseResponseStatus.SUCCESS;
import static com.dungi.common.util.StringUtil.LOGIN_USER;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    @PostMapping(
            value = "/user",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public BaseResponse<?> join(
            @Valid JoinRequestDto request
    ) throws Exception {

            userService.createUser(
                    request.getEmail(),
                    request.getImg(),
                    request.getPassword(),
                    request.getName(),
                    request.getNickname(),
                    request.getPhoneNumber()
            );
            return new BaseResponse<>(SUCCESS);
    }

    @PostMapping("/check/email")
    public BaseResponse<?> checkEmail(
            @RequestBody @Valid CheckEmailRequestDto requestDto
    ) throws Exception {
            userService.checkEmailPresent(requestDto.getEmail());
            return new BaseResponse<>(SUCCESS);
    }

    @PostMapping(value = "/phone")
    public BaseResponse<?> sendSms(
            @RequestBody @Valid SendSmsRequestDto requestDto
    ) throws Exception {
            userService.sendSms(requestDto.getPhoneNumber());
            return new BaseResponse<>(SUCCESS);
    }

    @PostMapping("/check/phone")
    public BaseResponse<?> checkCode(
            @RequestBody @Valid CheckCodeRequestDto requestDto
    ) throws Exception {
            userService.compareCode(requestDto.getCode(), requestDto.getPhoneNumber());
            return new BaseResponse<>(SUCCESS);
    }

    @PostMapping(
            value = "/kakao/user",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public BaseResponse<?> kakaoJoin(
            @Valid SnsJoinRequestDto requestDto) throws Exception {
            userService.createSnsUser(
                    requestDto.getEmail(),
                    requestDto.getNickname(),
                    requestDto.getKakaoImg(),
                    requestDto.getAccess_token(),
                    requestDto.getProfileImg()
            );
            return new BaseResponse<>(SUCCESS);
    }

    @GetMapping("/kakao/callback")
    public BaseResponse<?> kakaoOauth(
            @RequestParam String code
    ) throws Exception {
            return new BaseResponse<>(userService.snsToken(code));
    }

    @PostMapping("/login")
    public BaseResponse<?> login(
            @RequestBody @Valid LoginRequestDto requestDto,
            HttpSession session
    ) throws Exception {
        var user = userService.login(requestDto.getEmail(), requestDto.getPassword());
        session.setAttribute(LOGIN_USER, user);
        String token = tokenProvider.createToken(user.getId(), user.getEmail());
        return new BaseResponse<>(token);
    }

    @PostMapping("/kakao/login")
    public BaseResponse<?> kakaoLogin(
            @RequestBody @Valid SnsLoginRequestDto requestDto,
            HttpSession session
    ) throws Exception {
        var user = userService.snsLogin(requestDto.getEmail(), requestDto.getAccess_token());
        session.setAttribute(LOGIN_USER, user);
        String token = tokenProvider.createToken(user.getId(), user.getEmail());
        return new BaseResponse<>(token);
    }
}

