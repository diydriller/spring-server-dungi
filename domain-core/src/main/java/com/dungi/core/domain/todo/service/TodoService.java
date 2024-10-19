package com.dungi.core.domain.todo.service;

import com.dungi.core.domain.todo.dto.GetRepeatTodoDto;
import com.dungi.core.domain.todo.model.TodayTodo;

import java.util.List;


public interface TodoService {
    void createTodayTodo(String todoItem, String time, Long userId, Long roomId);

    void createRepeatTodo(String todoItem, String time, String days, Long userId, Long roomId);

    List<TodayTodo> getTodayTodo(Long userId, Long roomId, int page, int size);

    List<GetRepeatTodoDto> getRepeatTodo(Long userId, Long roomId, int page, int size);

    List<Long> findBestMember(Long roomId);
}
