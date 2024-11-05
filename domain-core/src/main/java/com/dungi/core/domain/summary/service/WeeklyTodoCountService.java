package com.dungi.core.domain.summary.service;

import com.dungi.core.domain.summary.dto.WeeklyTodoCountDto;

public interface WeeklyTodoCountService {
    WeeklyTodoCountDto getWeeklyTodoCount(Long roomId);
}
