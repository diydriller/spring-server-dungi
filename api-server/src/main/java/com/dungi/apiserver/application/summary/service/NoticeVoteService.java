package com.dungi.apiserver.application.summary.service;

import com.dungi.core.domain.summary.query.NoticeVoteDetail;
import com.dungi.core.integration.store.room.RoomStore;
import com.dungi.core.integration.store.summary.NoticeVoteStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeVoteService {
    private final RoomStore roomStore;
    private final NoticeVoteStore noticeVoteStore;

    @Transactional(readOnly = true)
    public List<NoticeVoteDetail> getNoticeVote(Long roomId, Long userId, int page, int size) {
        roomStore.getRoomEnteredByUser(userId, roomId);
        return noticeVoteStore.getNoticeVote(roomId, userId, page, size);
    }
}
