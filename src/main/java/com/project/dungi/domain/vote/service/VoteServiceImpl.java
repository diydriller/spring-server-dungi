package com.project.dungi.domain.vote.service;

import com.project.dungi.domain.common.FinishStatus;
import com.project.dungi.domain.room.service.RoomStore;
import com.project.dungi.domain.vote.dto.GetVoteItemDto;
import com.project.dungi.domain.vote.model.Vote;
import com.project.dungi.domain.vote.model.VoteItem;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class VoteServiceImpl implements VoteService{

    private final VoteStore voteStore;
    private final RoomStore roomStore;

    // 투표 생성 기능
    // 방에 유저 있는지 조회 - 투표 생성
    @Override
    @Transactional
    @CacheEvict(value="getNotiveVote", allEntries = true)
    public void createVote(String title, List<String> choiceArr, Long userId, Long roomId) {
        roomStore.getRoomEnteredByUser(userId, roomId);
        var voteItemList = choiceArr.stream()
                .map(VoteItem::new)
                .collect(Collectors.toList());
        var vote = Vote.builder()
                .title(title)
                .roomId(roomId)
                .userId(userId)
                .voteItems(voteItemList)
                .build();
        voteStore.saveVote(vote);
    }

    // 투표 조회 기능
    // 방에 유저 있는지 조회 - 투표 조회 - 투표 선택지와 투표한 사람들 조회 - 투표 안한 수 조회
    @Override
    @Transactional(readOnly = true)
    public GetVoteItemDto getVote(Long roomId, Long userId, Long voteId) {

        roomStore.getRoomEnteredByUser(userId, roomId);

        var vote = voteStore.getVote(voteId);

        List<GetVoteItemDto.VoteChoiceDto> voteChoiceDtoList = new ArrayList<>();
        List<Long> userChoiceList = new ArrayList<>();
        Set<Long> voteUserSet = new HashSet<>();
        var voteItemList = voteStore.getVoteItemList(vote);
        for(var voteItem : voteItemList){
            var voteUserList = voteStore.getVoteUser(voteItem.getId());
            for(var voteUser : voteUserList){
                voteUserSet.add(voteUser.getUserId());
                if(voteUser.getUserId().equals(userId)){
                    userChoiceList.add(voteItem.getId());
                }
            }
            voteChoiceDtoList.add(new GetVoteItemDto.VoteChoiceDto(voteItem.getChoice(), voteUserList));
        }

        int memberCnt = roomStore.getRoomMemberCnt(roomId);

        return GetVoteItemDto.builder()
                .title(vote.getTitle())
                .choiceIdList(userChoiceList)
                .isFinished(vote.getFinishStatus() == FinishStatus.FINISHED)
                .choice(voteChoiceDtoList)
                .isOwner(vote.getUserId().equals(userId))
                .unVotedMemberCnt(memberCnt-voteUserSet.size())
                .build();
    }

    // 투표 하기 기능
    // 투표 선택지 조회 - 투표하기
    @Transactional
    public void createVoteChoice(Long roomId, Long userId, Long voteId, Long choiceId) {
        roomStore.getRoomEnteredByUser(userId, roomId);
        var voteItem = voteStore.getVoteItem(choiceId);
        voteStore.createVoteChoice(userId, voteItem);
    }
}
