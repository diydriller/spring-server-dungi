package com.dungi.jpa.repository.summary;

import com.dungi.core.domain.summary.model.WeeklyTodoCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeeklyTodoCountJpaRepository extends JpaRepository<WeeklyTodoCount, Long> {
    Optional<WeeklyTodoCount> findByRoomIdAndUserIdAndYearAndWeekOfYearAndDayOfWeek(
            Long roomId,
            Long userId,
            Integer year,
            Integer weekOfYear,
            Integer dayOfWeek
    );
}
