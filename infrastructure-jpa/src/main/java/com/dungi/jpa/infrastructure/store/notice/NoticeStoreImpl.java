package com.dungi.core.infrastructure.store.notice;

import com.dungi.core.domain.notice.model.Notice;
import com.dungi.core.domain.notice.service.NoticeStore;
import com.dungi.core.infrastructure.jpa.notice.NoticeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NoticeStoreImpl implements NoticeStore {

    private final NoticeJpaRepository noticeJpaRepository;

    @Override
    public Notice saveNotice(Notice notice) {
        return noticeJpaRepository.save(notice);
    }
}
