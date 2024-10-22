package com.dungi.core.infrastructure.store.notice_vote;

import com.dungi.core.domain.notice_vote.dto.GetNoticeVoteDto;
import com.dungi.core.domain.notice_vote.model.NoticeVote;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeVoteStore {
    List<GetNoticeVoteDto> getNoticeVote(Long roomId, Long userId, int page, int size);

    void saveNoticeVote(NoticeVote noticeVote);
}
