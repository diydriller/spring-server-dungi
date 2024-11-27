package com.dungi.rdb.jpa.store.summary;

import com.dungi.core.domain.common.DeleteStatus;
import com.dungi.core.domain.summary.query.NoticeVoteDetail;
import com.dungi.core.integration.store.summary.NoticeVoteStore;
import com.dungi.rdb.jpa.repository.summary.NoticeVoteJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class NoticeVoteStoreImpl implements NoticeVoteStore {
    private final NoticeVoteJpaRepository noticeVoteJpaRepository;

    public List<NoticeVoteDetail> getNoticeVote(Long roomId, Long userId, int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "createdTime");
        return noticeVoteJpaRepository.findAllNoticeVote(
                roomId,
                DeleteStatus.NOT_DELETED,
                pageRequest
        );
    }

    public void saveNoticeVote(com.dungi.core.domain.summary.model.NoticeVote noticeVote) {
        noticeVoteJpaRepository.save(noticeVote);
    }
}