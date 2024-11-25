package com.dungi.core.integration.store.summary;

import com.dungi.core.domain.summary.dto.GetNoticeVoteDto;
import com.dungi.core.domain.summary.model.NoticeVote;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeVoteStore {
    List<GetNoticeVoteDto> getNoticeVote(Long roomId, Long userId, int page, int size);

    void saveNoticeVote(NoticeVote noticeVote);
}
