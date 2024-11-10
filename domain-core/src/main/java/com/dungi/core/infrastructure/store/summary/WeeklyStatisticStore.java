package com.dungi.core.infrastructure.store.summary;

import com.dungi.core.domain.summary.model.WeeklyTodoCount;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeeklyStatisticStore {
    Optional<WeeklyTodoCount> getWeeklyTodoCountByUniqueKeys(
            Long roomId,
            Long userId,
            Integer year,
            Integer weekOfYear,
            Integer dayOfWeek
    );

    void saveWeeklyTodoCount(WeeklyTodoCount weeklyTodoCount);

    List<WeeklyTodoCount> getWeeklyTodoCountListInRoom(
            Long roomId,
            Integer year,
            Integer weekOfYear,
            Integer dayOfWeek
    );

    List<WeeklyTodoCount> decideAndGetWeeklyTopUserInRoom(
            Long roomId,
            Integer year,
            Integer weekOfYear
    );
}
