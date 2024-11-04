package com.dungi.core.domain.summary.service;

import com.dungi.core.domain.summary.event.SaveNoticeVoteEvent;
import com.dungi.core.domain.summary.model.NoticeVote;
import com.dungi.core.infrastructure.store.summary.NoticeVoteStore;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class NoticeVoteEventListener {
    private final NoticeVoteStore noticeVoteStore;

    @Async
    @TransactionalEventListener
    public void saveNoticeVote(SaveNoticeVoteEvent event) {
        noticeVoteStore.saveNoticeVote(
                NoticeVote.builder()
                        .content(event.getContent())
                        .noticeVoteId(event.getId())
                        .createdTime(event.getCreatedTime())
                        .roomId(event.getRoomId())
                        .userId(event.getUserId())
                        .type(event.getType())
                        .build()
        );
    }
}