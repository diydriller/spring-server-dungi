package com.dungi.core.domain.vote.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class GetVoteItemDto {

    private String title;
    private List<GetVoteItemDto.VoteChoiceDto> choice;
    private List<Long> choiceIdList;
    private Boolean isOwner;
    private Boolean isFinished;
    private Integer unVotedMemberCnt;

    @Getter
    @AllArgsConstructor
    public static class VoteChoiceDto{
        private String choice;
        private List<VoteUserDto> voteUser;
    }
}
