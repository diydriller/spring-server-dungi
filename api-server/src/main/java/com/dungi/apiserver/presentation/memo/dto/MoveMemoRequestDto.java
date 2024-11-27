package com.dungi.apiserver.presentation.memo.dto;

import com.dungi.apiserver.application.memo.dto.MoveMemoDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;

@Getter
@NoArgsConstructor
public class MoveMemoRequestDto {
    @Digits(integer = 2, fraction = 5)
    private Double x;

    @Digits(integer = 2, fraction = 5)
    private Double y;

    public MoveMemoDto createMemoDto() {
        return MoveMemoDto.builder()
                .x(x)
                .y(y)
                .build();
    }
}
