package com.dungi.core.domain.notice.service;

public interface NoticeService {
    void createNotice(String noticeItem, Long userId, Long roomId);
}
