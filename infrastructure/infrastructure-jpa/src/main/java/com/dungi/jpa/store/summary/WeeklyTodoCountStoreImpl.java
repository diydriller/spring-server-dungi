package com.dungi.jpa.store.summary;

import com.dungi.core.domain.summary.model.WeeklyTodoCount;
import com.dungi.core.infrastructure.store.summary.WeeklyTodoCountStore;
import com.dungi.jpa.repository.summary.WeeklyTodoCountJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class WeeklyTodoCountStoreImpl implements WeeklyTodoCountStore {
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
    public List<WeeklyTodoCount> getWeeklyTodoCountListInRoom(Long roomId) {
        var now = LocalDate.now();
        var weekFields = WeekFields.ISO;
        var weekOfYear = now.get(weekFields.weekOfYear());
        var year = now.getYear();
        return weeklyTodoCountJpaRepository.findAllByRoomIdAndYearAndWeekOfYear(
                roomId,
                year,
                weekOfYear
        );
    }
}
