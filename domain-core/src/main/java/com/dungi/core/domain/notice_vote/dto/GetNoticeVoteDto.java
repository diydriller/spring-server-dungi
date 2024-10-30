package com.dungi.core.domain.notice_vote.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class GetNoticeVoteDto {
    private String type;
    private Long id;
    private String profileImg;
    private String content;
    private Long userId;
    private LocalDateTime createdAt;
}
