package com.dungi.apiserver.application.user.service;

import com.dungi.apiserver.application.user.dto.CreateSnsUserDto;
import com.dungi.apiserver.application.user.dto.CreateUserDto;
import com.dungi.common.exception.BaseException;
import com.dungi.common.util.StringUtil;
import com.dungi.core.domain.user.model.User;
import com.dungi.core.integration.file.FileUploader;
import com.dungi.core.integration.sms.SmsSender;
import com.dungi.core.integration.sns.SnsService;
import com.dungi.core.integration.store.user.UserCacheStore;
import com.dungi.core.integration.store.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.dungi.common.response.BaseResponseStatus.*;
import static com.dungi.common.util.NumberUtil.CODE_DURATION;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final FileUploader fileUploader;
    private final UserStore userStore;
    private final UserCacheStore userCacheStore;
    private final SnsService snsService;
    private final SmsSender smsSender;

    // 회원가입 기능
    // 이메일 중복 여부 - 이미지 업로드 - 비밀번호 암호화 - 유저 저장
    @Transactional
    public void createUser(CreateUserDto dto) throws Exception {
        checkEmailPresent(dto.getEmail());
        String imageDownUrl = fileUploader.imageUpload(dto.getImg());
        String hashedPassword = passwordEncoder.encode(dto.getPassword());
        var user = User.builder()
                .email(dto.getEmail())
                .password(hashedPassword)
                .name(dto.getName())
                .phoneNumber(dto.getPhoneNumber())
                .nickname(dto.getNickname())
                .profileImg(imageDownUrl)
                .provider("local")
                .build();
        userStore.saveUser(user);
    }

    // SMS 인증번호 보내기 기능
    // 인증번호 생성 - 암호화 - 인증번호 저장 - sms 전송
    @Transactional(rollbackFor = Exception.class)
    public void sendSms(String phoneNumber) {
        String randomNumber = StringUtil.randomNumber();
        String trimmedPhoneNumber = StringUtil.trimPhoneNumber(phoneNumber);
        userCacheStore.saveCode(trimmedPhoneNumber, randomNumber, CODE_DURATION);
        smsSender.sendSms(trimmedPhoneNumber, randomNumber);
    }

    // SMS 인증번호 검증 기능
    // 인증번호 조회 - 인증번호 비교
    @Transactional(readOnly = true)
    public void compareCode(String code, String phoneNumber) {
        String trimmedPhoneNumber = StringUtil.trimPhoneNumber(phoneNumber);
        String savedCode = userCacheStore.getCode(trimmedPhoneNumber);
        if (savedCode.equals(code)) {
            throw new BaseException(CODE_NOT_EQUAL);
        }
    }

    // SNS 회원가입 기능
    // 이메일 검증 - 이메일 중복 확인 - 이미지 업로드 - 유저 저장
    @Transactional
    public void createSnsUser(CreateSnsUserDto dto) throws Exception {
        String kakaoEmail = snsService.getSnsInfo(dto.getAccessToken());
        if (!dto.getEmail().equals(kakaoEmail)) {
            throw new BaseException(NOT_EXISTS_EMAIL);
        }
        checkEmailPresent(dto.getEmail());
        String imageDownUrl = dto.getKakaoImg();
        if (imageDownUrl == null) {
            imageDownUrl = fileUploader.imageUpload(dto.getProfileImg());
        }

        User user = User.builder()
                .nickname(dto.getNickname())
                .email(dto.getEmail())
                .profileImg(imageDownUrl)
                .provider("kakao")
                .build();
        userStore.saveUser(user);
    }

    // 로그인 기능
    // 이메일 조회 - 비밀번호 일치여부
    @Transactional(readOnly = true)
    public User login(String email, String password) {
        User user = userStore.getUserByEmail(email);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BaseException(PASSWORD_NOT_EQUAL);
        }
        return user;
    }

    // SNS 로그인 기능
    // 이메일 검증 - 이메일 일치여부 - 이메일 조회
    @Transactional(readOnly = true)
    public User snsLogin(String email, String accessToken)
            throws Exception {
        String kakaoEmail = snsService.getSnsInfo(accessToken);
        if (!email.equals(kakaoEmail)) {
            throw new BaseException(KAKAO_LOGIN_FAIL);
        }
        return userStore.getUserByEmail(email);
    }

    // 카카오 토큰 가져오기 메서드
    public String snsToken(String code) throws Exception {
        return snsService.getSnsToken(code);
    }

    // 이메일 조회 메서드
    public void checkEmailPresent(String email) {
        userStore.checkEmailPresent(email);
    }
}
