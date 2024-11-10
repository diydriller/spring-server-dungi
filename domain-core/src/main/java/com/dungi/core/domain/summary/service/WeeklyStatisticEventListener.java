package com.dungi.core.domain.summary.service;

import com.dungi.core.domain.summary.event.UpdateWeeklyTodoCountEvent;
import com.dungi.core.domain.summary.model.WeeklyTodoCount;
import com.dungi.core.infrastructure.store.summary.WeeklyStatisticStore;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDate;
import java.time.temporal.WeekFields;

@RequiredArgsConstructor
@Component
public class WeeklyStatisticEventListener {
    private final WeeklyStatisticStore weeklyStatisticStore;

    @Async
    @TransactionalEventListener
    public void updateWeeklyTodoCount(UpdateWeeklyTodoCountEvent event) {
        var now = LocalDate.now();
        var weekFields = WeekFields.ISO;
        var weekOfYear = now.get(weekFields.weekOfYear());
        var dayOfWeek = now.get(weekFields.dayOfWeek());
        var year = now.getYear();

        weeklyStatisticStore.getWeeklyTodoCountByUniqueKeys(
                event.getRoomId(),
                event.getUserId(),
                year,
                weekOfYear,
                dayOfWeek
        ).ifPresentOrElse(
                WeeklyTodoCount::addTodoCount,
                () -> {
                    var weeklyTodoCount = WeeklyTodoCount.builder()
                            .roomId(event.getRoomId())
                            .userId(event.getUserId())
                            .year(year)
                            .todoCount(1L)
                            .weekOfYear(weekOfYear)
                            .dayOfWeek(dayOfWeek)
                            .build();
                    weeklyStatisticStore.saveWeeklyTodoCount(weeklyTodoCount);
                }
        );
    }
}
