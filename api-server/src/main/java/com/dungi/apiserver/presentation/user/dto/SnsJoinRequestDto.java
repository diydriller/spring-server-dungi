package com.dungi.apiserver.presentation.user.dto;

import com.dungi.apiserver.application.user.dto.CreateSnsUserDto;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class SnsJoinRequestDto {

    @NotEmpty(message = "email is empty")
    @Pattern(regexp = "\\w+@\\w+.\\w+", message = "email format is wrong")
    private String email;

    @NotEmpty(message = "nickname is empty")
    @Size(max = 10, message = "nickname's max length is 10")
    private String nickname;

    private String kakaoImg;

    @NotEmpty(message = "token is empty")
    private String accessToken;

    private MultipartFile profileImg;

    public CreateSnsUserDto createSnsUserDto() {
        return CreateSnsUserDto.builder()
                .nickname(nickname)
                .profileImg(profileImg)
                .accessToken(accessToken)
                .kakaoImg(kakaoImg)
                .email(email)
                .build();
    }
}
