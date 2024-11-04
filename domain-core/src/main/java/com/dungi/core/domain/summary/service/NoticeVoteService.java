package com.dungi.core.domain.summary.service;

import com.dungi.core.domain.summary.dto.GetNoticeVoteDto;

import java.util.List;

public interface NoticeVoteService {
    List<GetNoticeVoteDto> getNoticeVote(Long roomId, Long userId, int page, int size);
}
