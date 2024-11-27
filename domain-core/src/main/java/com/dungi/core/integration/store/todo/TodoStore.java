package com.dungi.core.integration.store.todo;

import com.dungi.core.domain.todo.query.TodoStatistic;
import com.dungi.core.domain.todo.model.RepeatDay;
import com.dungi.core.domain.todo.model.RepeatTodo;
import com.dungi.core.domain.todo.model.TodayTodo;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TodoStore {
    void saveTodayTodo(TodayTodo todo);

    void saveRepeatTodo(RepeatTodo todo, List<RepeatDay> repeatDayList);

    List<TodayTodo> findTodayTodo(Long roomId, Long userId, int page, int size);

    List<RepeatTodo> findRepeatTodo(Long roomId, Long userId, int page, int size);

    List<TodoStatistic> findAllMemberTodoCount(
            List<Long> userIdList,
            LocalDateTime startDate,
            LocalDateTime endDate
    );

    TodayTodo findTodayTodo(Long roomId, Long todoId);
}
