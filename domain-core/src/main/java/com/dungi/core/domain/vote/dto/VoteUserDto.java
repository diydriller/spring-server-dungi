package com.dungi.core.domain.vote.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VoteUserDto {
    private String profileImg;
    private String nickname;
    private Long userId;
    private String choice;
    private Long voteItemId;
}
