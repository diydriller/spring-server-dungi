package com.dungi.core.domain.summary.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UpdateWeeklyTodoCountEvent {
    private Long userId;

    private Long roomId;
}
