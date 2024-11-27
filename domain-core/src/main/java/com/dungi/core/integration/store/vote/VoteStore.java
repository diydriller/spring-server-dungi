package com.dungi.core.integration.store.vote;

import com.dungi.core.domain.vote.query.VoteUserDetail;
import com.dungi.core.domain.vote.model.Vote;
import com.dungi.core.domain.vote.model.VoteItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteStore {
    Vote saveVote(Vote vote, List<VoteItem> voteItemList);

    Vote getVote(Long voteId);

    List<VoteItem> getVoteItemList(Vote vote);

    List<VoteUserDetail> getVoteUser(List<VoteItem> voteItemList);

    void createVoteChoice(Long userId, VoteItem voteItem);

    VoteItem getVoteItem(Long choiceId, Long voteId);
}
