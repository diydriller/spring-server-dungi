package com.dungi.apiserver.application.notice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class CreateNoticeRequestDto {
    @NotEmpty(message = "notice is empty")
    private String notice;
}
