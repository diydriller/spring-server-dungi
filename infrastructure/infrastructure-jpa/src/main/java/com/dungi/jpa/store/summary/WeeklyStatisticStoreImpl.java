package com.dungi.jpa.store.summary;

import com.dungi.core.domain.summary.model.WeeklyTodoCount;
import com.dungi.core.infrastructure.store.summary.WeeklyStatisticStore;
import com.dungi.jpa.repository.summary.WeeklyTodoCountJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class WeeklyStatisticStoreImpl implements WeeklyStatisticStore {
    private final WeeklyTodoCountJpaRepository weeklyTodoCountJpaRepository;

    @Override
    public Optional<WeeklyTodoCount> getWeeklyTodoCountByUniqueKeys(
            Long roomId,
            Long userId,
            Integer year,
            Integer weekOfYear,
            Integer dayOfWeek
    ) {
        return weeklyTodoCountJpaRepository.findByRoomIdAndUserIdAndYearAndWeekOfYearAndDayOfWeek(
                roomId,
                userId,
                year,
                weekOfYear,
                dayOfWeek
        );
    }

    @Override
    public void saveWeeklyTodoCount(WeeklyTodoCount weeklyTodoCount) {
        weeklyTodoCountJpaRepository.save(weeklyTodoCount);
    }

    @Override
    public List<WeeklyTodoCount> getWeeklyTodoCountListInRoom(
            Long roomId,
            Integer year,
            Integer weekOfYear,
            Integer dayOfWeek
    ) {
        return weeklyTodoCountJpaRepository.findAllByRoomIdAndYearAndWeekOfYear(
                roomId,
                year,
                weekOfYear
        );
    }

    @Override
    public List<WeeklyTodoCount> decideAndGetWeeklyTopUserInRoom(
            Long roomId,
            Integer year,
            Integer weekOfYear
    ) {
        return weeklyTodoCountJpaRepository.findAllMaxTodoCountUserByRoomIdAndYearAndWeekOfYear(
                roomId,
                year,
                weekOfYear
        );
    }
}
