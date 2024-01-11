package com.project.dungi.domain.notice.service;

import com.project.dungi.domain.notice.model.Notice;

public interface NoticeStore {
    Notice saveNotice(Notice notice);
}
