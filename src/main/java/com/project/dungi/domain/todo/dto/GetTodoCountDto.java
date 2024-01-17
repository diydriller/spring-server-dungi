package com.project.dungi.domain.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetTodoCountDto {
    private Long userId;
    private Long todoCount;
}
