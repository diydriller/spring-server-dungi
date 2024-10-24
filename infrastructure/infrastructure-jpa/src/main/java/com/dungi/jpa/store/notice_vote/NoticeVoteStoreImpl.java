package com.dungi.jpa.store.notice_vote;

import com.dungi.core.domain.common.DeleteStatus;
import com.dungi.core.domain.notice_vote.dto.GetNoticeVoteDto;
import com.dungi.core.domain.notice_vote.model.NoticeVote;
import com.dungi.core.infrastructure.store.notice_vote.NoticeVoteStore;
import com.dungi.jpa.repository.notice_vote.NoticeVoteJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class NoticeVoteStoreImpl implements NoticeVoteStore {
    private final NoticeVoteJpaRepository noticeVoteJpaRepository;

    public List<GetNoticeVoteDto> getNoticeVote(Long roomId, Long userId, int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "createdTime");
        return noticeVoteJpaRepository.findAllNoticeVote(
                roomId,
                DeleteStatus.NOT_DELETED,
                pageRequest
        );
    }

    public void saveNoticeVote(NoticeVote noticeVote) {
        noticeVoteJpaRepository.save(noticeVote);
    }
}