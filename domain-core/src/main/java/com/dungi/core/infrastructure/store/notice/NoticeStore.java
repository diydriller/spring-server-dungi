package com.dungi.core.infrastructure.store.notice;

import com.dungi.core.domain.notice.model.Notice;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeStore {
    Notice saveNotice(Notice notice);
}
