package com.dungi.core.infrastructure.store.vote;

import com.dungi.core.domain.vote.dto.VoteUserDto;
import com.dungi.core.domain.vote.model.Vote;
import com.dungi.core.domain.vote.model.VoteItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteStore {
    Vote saveVote(Vote vote, List<VoteItem> voteItemList);

    Vote getVote(Long voteId);

    List<VoteItem> getVoteItemList(Vote vote);

    List<VoteUserDto> getVoteUser(List<VoteItem> voteItemList);

    void createVoteChoice(Long userId, VoteItem voteItem);

    VoteItem getVoteItem(Long choiceId, Long voteId);
}
