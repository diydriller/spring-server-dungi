package com.project.dungi.domain.vote.service;

import com.project.dungi.domain.vote.dto.VoteUserDto;
import com.project.dungi.domain.vote.model.Vote;
import com.project.dungi.domain.vote.model.VoteItem;

import java.util.List;

public interface VoteStore {
    void saveVote(Vote vote);
    Vote getVote(Long voteId);
    List<VoteItem> getVoteItemList(Vote vote);
    List<VoteUserDto> getVoteUser(List<VoteItem> voteItemList);
    void createVoteChoice(Long userId, VoteItem voteItem);
    VoteItem getVoteItem(Long choiceId);
}
