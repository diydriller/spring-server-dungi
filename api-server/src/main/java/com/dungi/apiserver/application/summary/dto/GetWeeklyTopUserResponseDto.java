package com.dungi.apiserver.application.summary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetWeeklyTopUserResponseDto {
    private Long memberId;
    private String memberNickname;
    private String memberImageUrl;
}
