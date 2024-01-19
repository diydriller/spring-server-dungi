package com.dungi.core.domain.notice.service;

import com.dungi.core.domain.notice.model.Notice;

public interface NoticeStore {
    Notice saveNotice(Notice notice);
}
