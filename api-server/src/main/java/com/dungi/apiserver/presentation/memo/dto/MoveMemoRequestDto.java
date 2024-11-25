package com.dungi.apiserver.presentation.memo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;

@Getter
@NoArgsConstructor
public class MoveMemoRequestDto {
    @Digits(integer = 2,fraction = 5)
    private double x;

    @Digits(integer = 2,fraction = 5)
    private double y;
}
