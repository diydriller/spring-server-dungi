package com.project.dungi.domain.notice.service;

import com.project.dungi.domain.notice.model.Notice;
import com.project.dungi.domain.room.service.RoomStore;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{

    private final RoomStore roomStore;
    private final NoticeStore noticeStore;

    @Transactional
    @CacheEvict(value="getNotiveVote",allEntries = true)
    public void createNotice(String noticeItem, Long userId, Long roomId) {
        roomStore.getRoomEnteredByUser(userId, roomId);
        var notice = Notice.builder()
                .noticeItem(noticeItem)
                .roomId(roomId)
                .userId(userId)
                .build();
        noticeStore.saveNotice(notice);
    }
}
