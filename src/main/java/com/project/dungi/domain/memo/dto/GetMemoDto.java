package com.project.dungi.domain.memo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class GetMemoDto {
    private Long id;
    private String profileImg;
    private String memoItem;
    private String memoColor;
    private LocalDateTime createdTime;
    private double xPosition;
    private double yPosition;
    private Long userId;
}
