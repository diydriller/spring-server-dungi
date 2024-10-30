package com.dungi.core.domain.vote.service;

import com.dungi.core.domain.vote.dto.GetVoteItemDto;

import java.util.List;

public interface VoteService {
    void createVote(String title, List<String> choiceArr, Long userId, Long roomId);

    GetVoteItemDto getVote(Long roomId, Long userId, Long voteId);

    void createVoteChoice(Long roomId, Long userId, Long voteId, Long choiceId);
}
