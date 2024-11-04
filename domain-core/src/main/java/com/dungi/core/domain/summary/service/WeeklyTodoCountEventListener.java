package com.dungi.core.domain.summary.service;

import com.dungi.core.domain.summary.event.UpdateWeeklyTodoCountEvent;
import com.dungi.core.domain.summary.model.WeeklyTodoCount;
import com.dungi.core.infrastructure.store.summary.WeeklyTodoCountStore;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

@RequiredArgsConstructor
@Component
public class WeeklyTodoCountEventListener {
    private final WeeklyTodoCountStore weeklyTodoCountStore;

    @Async
    @TransactionalEventListener
    public void updateWeeklyTodoCount(UpdateWeeklyTodoCountEvent event) {
        var now = LocalDate.now();
        var weekFields = WeekFields.of(Locale.getDefault());
        var weekOfYear = now.get(weekFields.weekOfYear());
        var dayOfWeek = now.get(weekFields.dayOfWeek());
        var year = now.getYear();

        weeklyTodoCountStore.getWeeklyTodoCountByUniqueKeys(
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
                    weeklyTodoCountStore.saveWeeklyTodoCount(weeklyTodoCount);
                }
        );
    }
}
