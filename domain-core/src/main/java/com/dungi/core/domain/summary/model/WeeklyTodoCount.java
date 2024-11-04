package com.dungi.core.domain.summary.model;

import com.dungi.core.domain.common.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Entity
@Table(
        name = "weekly_todo_count",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"roomId", "userId", "year", "weekOfYear", "dayOfWeek"})
        }
)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WeeklyTodoCount extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long roomId;

    private Integer year;

    private Integer weekOfYear;

    private Integer dayOfWeek;

    private Long todoCount;

    public void addTodoCount() {
        this.todoCount++;
    }
}
