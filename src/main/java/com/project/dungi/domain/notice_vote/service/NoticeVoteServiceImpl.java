package com.project.dungi.domain.notice_vote.service;

import com.project.dungi.domain.notice_vote.dto.GetNoticeVoteDto;
import com.project.dungi.domain.room.service.RoomStore;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeVoteServiceImpl implements NoticeVoteService{

    private final RoomStore roomStore;
    private final NoticeVoteStore noticeVoteStore;

    @Cacheable(key = "{#roomId,#page}",value="getNotiveVote")
    @Transactional(isolation = Isolation.REPEATABLE_READ, readOnly = true)
    public List<GetNoticeVoteDto> getNoticeVote(Long roomId, Long userId, int page, int size) {
        roomStore.getRoomEnteredByUser(userId, roomId);
        return noticeVoteStore.getNoticeVote(roomId, userId, page, size);
    }
}
