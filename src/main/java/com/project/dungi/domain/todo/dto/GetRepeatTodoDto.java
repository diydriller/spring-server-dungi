package com.project.dungi.domain.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class GetRepeatTodoDto {
    private Long todoId;
    private String todo;
    private LocalDateTime deadline;
    private Long userId;
    private String day;
}
