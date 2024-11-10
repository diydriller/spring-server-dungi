package com.dungi.core.domain.summary.service;

import com.dungi.core.domain.summary.dto.WeeklyTodoCountDto;
import com.dungi.core.domain.summary.model.WeeklyTodoCount;

import java.util.List;

public interface WeeklyStatisticService {
    WeeklyTodoCountDto getWeeklyTodoCount(Long roomId);
    List<WeeklyTodoCount> decideAndGetWeeklyTopUserInRoom(Long roomId);
}
