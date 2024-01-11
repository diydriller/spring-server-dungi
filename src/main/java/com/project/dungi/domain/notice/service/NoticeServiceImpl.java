package com.project.dungi.domain.notice.service;

import com.project.dungi.domain.notice.model.Notice;
import com.project.dungi.domain.notice_vote.model.NoticeVote;
import com.project.dungi.domain.notice_vote.service.NoticeVoteStore;
import com.project.dungi.domain.room.service.RoomStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{

    private final RoomStore roomStore;
    private final NoticeStore noticeStore;
    private final NoticeVoteStore noticeVoteStore;

    @Transactional
    public void createNotice(String noticeItem, Long userId, Long roomId) {
        roomStore.getRoomEnteredByUser(userId, roomId);
        var notice = Notice.builder()
                .noticeItem(noticeItem)
                .roomId(roomId)
                .userId(userId)
                .build();
        var savedNotice = noticeStore.saveNotice(notice);
        var noticeVote = NoticeVote.builder()
                .content(noticeItem)
                .createdTime(savedNotice.getCreatedTime())
                .roomId(roomId)
                .userId(userId)
                .noticeVoteId(savedNotice.getId())
                .type("N")
                .build();
        noticeVoteStore.saveNoticeVote(noticeVote);
    }
}
