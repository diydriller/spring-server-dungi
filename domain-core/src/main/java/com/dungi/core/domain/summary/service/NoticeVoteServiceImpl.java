package com.dungi.core.domain.summary.service;

import com.dungi.core.domain.summary.dto.GetNoticeVoteDto;
import com.dungi.core.infrastructure.store.summary.NoticeVoteStore;
import com.dungi.core.infrastructure.store.room.RoomStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeVoteServiceImpl implements NoticeVoteService {
    private final RoomStore roomStore;
    private final NoticeVoteStore noticeVoteStore;

    @Transactional(readOnly = true)
    public List<GetNoticeVoteDto> getNoticeVote(Long roomId, Long userId, int page, int size) {
        roomStore.getRoomEnteredByUser(userId, roomId);
        return noticeVoteStore.getNoticeVote(roomId, userId, page, size);
    }
}
