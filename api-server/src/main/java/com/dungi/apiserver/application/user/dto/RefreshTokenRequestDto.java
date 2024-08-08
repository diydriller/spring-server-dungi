package com.dungi.apiserver.application.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class RefreshTokenRequestDto {
    @NotEmpty(message = "token is empty")
    private String refresh_token;
}
