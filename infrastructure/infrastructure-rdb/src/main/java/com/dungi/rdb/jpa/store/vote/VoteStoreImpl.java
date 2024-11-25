package com.dungi.rdb.jpa.store.vote;

import com.dungi.common.exception.BaseException;
import com.dungi.common.response.BaseResponseStatus;
import com.dungi.core.domain.common.DeleteStatus;
import com.dungi.core.domain.vote.dto.VoteUserDto;
import com.dungi.core.domain.vote.model.UserVoteItem;
import com.dungi.core.domain.vote.model.Vote;
import com.dungi.core.domain.vote.model.VoteItem;
import com.dungi.core.integration.store.vote.VoteStore;
import com.dungi.rdb.jpa.repository.vote.UserVoteItemJpaRepository;
import com.dungi.rdb.jpa.repository.vote.VoteItemJdbcRepository;
import com.dungi.rdb.jpa.repository.vote.VoteItemJpaRepository;
import com.dungi.rdb.jpa.repository.vote.VoteJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class VoteStoreImpl implements VoteStore {
    private final VoteJpaRepository voteJpaRepository;
    private final VoteItemJpaRepository voteItemJpaRepository;
    private final UserVoteItemJpaRepository userVoteItemJpaRepository;
    private final VoteItemJdbcRepository voteItemJdbcRepository;

    @Override
    public Vote saveVote(Vote vote, List<VoteItem> voteItemList) {
        var savedVote = voteJpaRepository.save(vote);
        voteItemList.forEach(vi -> vi.setVote(savedVote));
        voteItemJdbcRepository.saveAll(voteItemList);
        return savedVote;
    }

    @Override
    public Vote getVote(Long voteId) {
        return voteJpaRepository.findByIdAndDeleteStatus(voteId, DeleteStatus.NOT_DELETED)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXIST_VOTE));
    }

    @Override
    public VoteItem getVoteItem(Long choiceId, Long voteId) {
        return voteItemJpaRepository.findVoteItem(choiceId, voteId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXIST_VOTEITEM));
    }

    @Override
    public List<VoteItem> getVoteItemList(Vote vote) {
        return voteItemJpaRepository.getVoteItemList(vote);
    }

    @Override
    public List<VoteUserDto> getVoteUser(List<VoteItem> voteItemList) {
        return userVoteItemJpaRepository.getVoteUser(voteItemList, DeleteStatus.NOT_DELETED);
    }

    @Override
    public void createVoteChoice(Long userId, VoteItem voteItem) {
        userVoteItemJpaRepository.findByUserAndVoteItem(userId, voteItem)
                .ifPresentOrElse(
                        (uvi) -> {
                            uvi.changeChoice();
                            userVoteItemJpaRepository.save(uvi);
                        }, () -> {
                            var userVoteItem = new UserVoteItem(userId, voteItem);
                            userVoteItemJpaRepository.save(userVoteItem);
                        }
                );
    }
}
