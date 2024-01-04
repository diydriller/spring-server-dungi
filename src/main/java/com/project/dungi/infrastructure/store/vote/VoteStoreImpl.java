package com.project.dungi.infrastructure.store.vote;

import com.project.dungi.common.exception.BaseException;
import com.project.dungi.domain.common.DeleteStatus;
import com.project.dungi.domain.vote.dto.VoteUserDto;
import com.project.dungi.domain.vote.model.UserVoteItem;
import com.project.dungi.domain.vote.model.Vote;
import com.project.dungi.domain.vote.model.VoteItem;
import com.project.dungi.domain.vote.service.VoteStore;
import com.project.dungi.infrastructure.jpa.vote.UserVoteItemJpaRepository;
import com.project.dungi.infrastructure.jpa.vote.VoteItemJpaRepository;
import com.project.dungi.infrastructure.jpa.vote.VoteJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.project.dungi.common.response.BaseResponseStatus.NOT_EXIST_VOTE;
import static com.project.dungi.common.response.BaseResponseStatus.NOT_EXIST_VOTEITEM;

@RequiredArgsConstructor
@Component
public class VoteStoreImpl implements VoteStore {

    private final VoteJpaRepository voteJpaRepository;
    private final VoteItemJpaRepository voteItemJpaRepository;
    private final UserVoteItemJpaRepository userVoteItemJpaRepository;

    @Override
    public void saveVote(Vote vote) {
        voteJpaRepository.save(vote);
    }

    @Override
    public Vote getVote(Long voteId) {
        return voteJpaRepository.findByIdAndDeleteStatus(voteId, DeleteStatus.NOT_DELETED)
                .orElseThrow(() -> {
                    throw new BaseException(NOT_EXIST_VOTE);
                });
    }

    @Override
    public VoteItem getVoteItem(Long choiceId) {
        return voteItemJpaRepository.findById(choiceId)
                .orElseThrow(() -> {
                    throw new BaseException(NOT_EXIST_VOTEITEM);
                });
    }

    @Override
    public List<VoteItem> getVoteItemList(Vote vote) {
        return voteItemJpaRepository.getVoteItemList(vote);
    }

    @Override
    public List<VoteUserDto> getVoteUser(Long voteItemId) {
        return userVoteItemJpaRepository.getVoteUser(voteItemId, DeleteStatus.NOT_DELETED);
    }

    @Override
    public void createVoteChoice(Long userId, VoteItem voteItem) {
        userVoteItemJpaRepository.findByUserAndVoteItem(userId, voteItem)
                .ifPresentOrElse(
                        (uvi)->{
                            uvi.changeChoice();
                            userVoteItemJpaRepository.save(uvi);
                        },()->{
                            UserVoteItem userVoteItem = new UserVoteItem(userId, voteItem);
                            userVoteItemJpaRepository.save(userVoteItem);
                        }
                );
    }
}
