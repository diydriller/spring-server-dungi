package com.dungi.core.domain.notice_vote.service;

import com.dungi.core.domain.notice_vote.dto.GetNoticeVoteDto;

import java.util.List;

public interface NoticeVoteService {
    List<GetNoticeVoteDto> getNoticeVote(Long roomId, Long userId, int page, int size);
}
