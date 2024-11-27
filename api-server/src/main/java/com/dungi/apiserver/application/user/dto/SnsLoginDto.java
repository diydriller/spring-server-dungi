package com.dungi.apiserver.application.user.dto;

import com.dungi.common.value.Provider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SnsLoginDto {
    private String email;
    private String accessToken;
    private Provider serviceType;
}
