package com.dungi.core.domain.notice_vote.service;

import com.dungi.core.domain.notice_vote.dto.GetNoticeVoteDto;
import com.dungi.core.domain.notice_vote.model.NoticeVote;

import java.util.List;

public interface NoticeVoteStore {
    List<GetNoticeVoteDto> getNoticeVote(Long roomId, Long userId, int page, int size);
    void saveNoticeVote(NoticeVote noticeVote);
}
