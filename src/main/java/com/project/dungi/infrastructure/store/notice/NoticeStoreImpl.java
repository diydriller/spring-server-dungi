package com.project.dungi.infrastructure.store.notice;

import com.project.dungi.domain.notice.model.Notice;
import com.project.dungi.domain.notice.service.NoticeStore;
import com.project.dungi.infrastructure.jpa.notice.NoticeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NoticeStoreImpl implements NoticeStore {

    private final NoticeJpaRepository noticeJpaRepository;

    @Override
    public void saveNotice(Notice notice) {
        noticeJpaRepository.save(notice);
    }
}
