package com.dungi.apiserver.application.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetRepeatTodoResponseDto {
    private Long todoId;
    private String todo;
    private String deadline;
    private Boolean isOwner;
    private String day;
}
