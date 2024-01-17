package com.project.dungi.domain.notice.service;

import com.project.dungi.domain.notice.model.Notice;
import com.project.dungi.domain.notice_vote.event.SaveNoticeVoteEvent;
import com.project.dungi.domain.room.service.RoomStore;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.project.dungi.common.util.StringUtil.NOTICE_TYPE;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{

    private final RoomStore roomStore;
    private final NoticeStore noticeStore;
    private final ApplicationEventPublisher publisher;

    // 공지 생성 기능
    // 방에 유저 있는지 조회 - 공지 생성 - 조회용 테이블 데이터 생성
    @Transactional
    public void createNotice(String noticeItem, Long userId, Long roomId) {
        roomStore.getRoomEnteredByUser(userId, roomId);

        var notice = Notice.builder()
                .noticeItem(noticeItem)
                .roomId(roomId)
                .userId(userId)
                .build();
        var savedNotice = noticeStore.saveNotice(notice);

        publisher.publishEvent(
                SaveNoticeVoteEvent.builder()
                        .id(savedNotice.getId())
                        .type(NOTICE_TYPE)
                        .content(noticeItem)
                        .createdTime(savedNotice.getCreatedTime())
                        .roomId(roomId)
                        .userId(userId)
                        .build()
        );
    }
}
