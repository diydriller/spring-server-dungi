package com.project.dungi.domain.notice_vote.service;

import com.project.dungi.domain.notice_vote.dto.GetNoticeVoteDto;

import java.util.List;

public interface NoticeVoteStore {
    List<GetNoticeVoteDto> getNoticeVote(Long roomId, Long userId, int page, int size);
}
