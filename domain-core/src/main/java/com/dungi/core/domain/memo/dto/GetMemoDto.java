package com.dungi.core.domain.memo.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetMemoDto {
    private Long id;
    private String profileImg;
    private String memoItem;
    private String memoColor;
    private LocalDateTime createdTime;
    private Double xPosition;
    private Double yPosition;
    private Long userId;
}
