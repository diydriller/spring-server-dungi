package com.dungi.core.domain.summary.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class SaveNoticeVoteEvent {
    private Long id;
    private Long userId;
    private Long roomId;
    private String type;
    private String content;
    private LocalDateTime createdTime;
}
