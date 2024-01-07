package com.project.dungi.domain.todo.service;

import com.project.dungi.domain.todo.model.RepeatDay;
import com.project.dungi.domain.todo.model.RepeatTodo;
import com.project.dungi.domain.todo.model.TodayTodo;

import java.util.List;

public interface TodoStore {
    void saveTodayTodo(TodayTodo todo);
    void saveRepeatTodo(RepeatTodo todo, List<RepeatDay> repeatDayList);
    List<TodayTodo> findTodayTodo(Long roomId, Long userId, int page, int size);
    List<RepeatTodo> findRepeatTodo(Long roomId, Long userId, int page, int size);
}
