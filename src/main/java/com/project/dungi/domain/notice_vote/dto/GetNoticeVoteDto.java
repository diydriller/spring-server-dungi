package com.project.dungi.domain.notice_vote.dto;

import java.time.LocalDateTime;


public interface GetNoticeVoteDto {
    String getType();
    Long getId();
    String getProfileImg();
    String getContent();
    Long getUserId();
    LocalDateTime getCreatedAt();
}
