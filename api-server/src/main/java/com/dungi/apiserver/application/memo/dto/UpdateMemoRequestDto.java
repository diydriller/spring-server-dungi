package com.dungi.apiserver.application.memo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class UpdateMemoRequestDto {
    @NotEmpty(message = "memo is empty")
    private String memo;

    @NotEmpty(message = "color is empty")
    private String memoColor;
}
