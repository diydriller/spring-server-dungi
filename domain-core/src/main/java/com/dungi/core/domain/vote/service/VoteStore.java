package com.dungi.core.domain.vote.service;

import com.dungi.core.domain.vote.dto.VoteUserDto;
import com.dungi.core.domain.vote.model.Vote;
import com.dungi.core.domain.vote.model.VoteItem;

import java.util.List;

public interface VoteStore {
    Vote saveVote(Vote vote, List<VoteItem> voteItemList);
    Vote getVote(Long voteId);
    List<VoteItem> getVoteItemList(Vote vote);
    List<VoteUserDto> getVoteUser(List<VoteItem> voteItemList);
    void createVoteChoice(Long userId, VoteItem voteItem);
    VoteItem getVoteItem(Long choiceId, Long voteId);
}
