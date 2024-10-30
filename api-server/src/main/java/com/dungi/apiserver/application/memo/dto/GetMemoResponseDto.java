package com.dungi.apiserver.application.memo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetMemoResponseDto {
    private Long memoId;
    private String profileImg;
    private String memo;
    private String memoColor;
    private boolean isOwner;
    private String createdAt;
    private double x;
    private double y;
}
