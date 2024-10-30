package com.dungi.core.infrastructure.sns.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SnsTokenDto {
    private String access_token;
}
