package com.dungi.core.domain.memo.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class MemoDetail {
    private Long id;
    private String memoItem;
    private String memoColor;
    private LocalDateTime createdTime;
    private Double xPosition;
    private Double yPosition;
    private MemoUser memoUser;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class MemoUser{
        private Long userId;
        private String profileImg;
        private String nickname;
    }
}
