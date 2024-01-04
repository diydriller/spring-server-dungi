package com.project.dungi.infrastructure.store.notice_vote;

import com.project.dungi.domain.common.DeleteStatus;
import com.project.dungi.domain.notice_vote.service.NoticeVoteStore;
import com.project.dungi.domain.notice_vote.dto.GetNoticeVoteDto;
import com.project.dungi.infrastructure.jpa.notice_vote.NoticeVoteJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class NoticeVoteStoreImpl implements NoticeVoteStore {

    private final NoticeVoteJpaRepository noticeVoteJpaRepository;

    @Override
    public List<GetNoticeVoteDto> getNoticeVote(Long roomId, Long userId, int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size);
        return noticeVoteJpaRepository.findAllNoticeVote(
                roomId,
                DeleteStatus.NOT_DELETED,
                pageRequest
        );
    }
}