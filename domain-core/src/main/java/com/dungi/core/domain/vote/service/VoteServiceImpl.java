package com.dungi.core.domain.vote.service;

import com.dungi.core.domain.common.FinishStatus;
import com.dungi.core.domain.summary.event.SaveNoticeVoteEvent;
import com.dungi.core.domain.vote.dto.GetVoteItemDto;
import com.dungi.core.domain.vote.dto.VoteUserDto;
import com.dungi.core.domain.vote.model.Vote;
import com.dungi.core.domain.vote.model.VoteItem;
import com.dungi.core.infrastructure.store.room.RoomStore;
import com.dungi.core.infrastructure.store.vote.VoteStore;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.dungi.common.util.StringUtil.VOTE_TYPE;

@RequiredArgsConstructor
@Service
public class VoteServiceImpl implements VoteService {
    private final VoteStore voteStore;
    private final RoomStore roomStore;
    private final ApplicationEventPublisher publisher;

    // 투표 생성 기능
    // 방에 유저 있는지 조회 - 투표 생성 - 조회용 테이블 데이터 생성
    @Override
    @Transactional
    public void createVote(String title, List<String> choiceArr, Long userId, Long roomId) {
        roomStore.getRoomEnteredByUser(userId, roomId);

        var vote = Vote.builder()
                .title(title)
                .roomId(roomId)
                .userId(userId)
                .build();
        var voteItemList = choiceArr.stream()
                .map(VoteItem::new)
                .collect(Collectors.toList());
        var savedVote = voteStore.saveVote(vote, voteItemList);

        publisher.publishEvent(
                SaveNoticeVoteEvent.builder()
                        .content(title)
                        .createdTime(savedVote.getCreatedTime())
                        .userId(userId)
                        .roomId(roomId)
                        .type(VOTE_TYPE)
                        .id(savedVote.getId())
                        .build()
        );
    }

    // 투표 조회 기능
    // 방에 유저 있는지 조회 - 투표 조회 - 투표 선택지와 투표한 사람들 조회 - 투표 안한 수 조회
    @Override
    @Transactional(readOnly = true)
    public GetVoteItemDto getVote(Long roomId, Long userId, Long voteId) {

        roomStore.getRoomEnteredByUser(userId, roomId);

        var vote = voteStore.getVote(voteId);

        List<GetVoteItemDto.VoteChoiceDto> voteChoiceDtoList = new ArrayList<>();
        List<Long> myChoiceList = new ArrayList<>();
        Set<Long> voteUserIdSet = new HashSet<>();
        Map<String, List<VoteUserDto>> voteUserForChoiceMap = new HashMap<>();
        var voteItemList = voteStore.getVoteItemList(vote);

        var voteUserList = voteStore.getVoteUser(voteItemList);
        for (var voteUser : voteUserList) {
            voteUserIdSet.add(voteUser.getUserId());
            if (voteUser.getUserId().equals(userId)) {
                myChoiceList.add(voteUser.getVoteItemId());
            }
            voteUserForChoiceMap.putIfAbsent(voteUser.getChoice(), new ArrayList<>());
            voteUserForChoiceMap.get(voteUser.getChoice()).add(voteUser);
        }
        for (var voteItem : voteItemList) {
            var choice = voteItem.getChoice();
            List<VoteUserDto> voteUser = new ArrayList<>();
            if (voteUserForChoiceMap.containsKey(choice)) {
                voteUser = voteUserForChoiceMap.get(choice);
            }
            voteChoiceDtoList.add(
                    new GetVoteItemDto.VoteChoiceDto(choice, voteUser)
            );
        }

        int memberCnt = roomStore.getRoomMemberCnt(roomId);

        return GetVoteItemDto.builder()
                .title(vote.getTitle())
                .choiceIdList(myChoiceList)
                .isFinished(vote.getFinishStatus() == FinishStatus.FINISHED)
                .choice(voteChoiceDtoList)
                .isOwner(vote.getUserId().equals(userId))
                .unVotedMemberCnt(memberCnt - voteUserIdSet.size())
                .build();
    }

    // 투표 하기 기능
    // 투표 선택지 조회 - 투표하기
    @Transactional
    public void createVoteChoice(Long roomId, Long userId, Long voteId, Long choiceId) {
        roomStore.getRoomEnteredByUser(userId, roomId);
        var voteItem = voteStore.getVoteItem(choiceId, voteId);
        voteStore.createVoteChoice(userId, voteItem);
    }
}
