package com.dungi.apiserver.application.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
@AllArgsConstructor
public class CreateSnsUserDto {
    private String email;
    private String nickname;
    private String kakaoImg;
    private String accessToken;
    private MultipartFile profileImg;
}
