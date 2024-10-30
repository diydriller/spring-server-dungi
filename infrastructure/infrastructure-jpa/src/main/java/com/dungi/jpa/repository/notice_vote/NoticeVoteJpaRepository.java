package com.dungi.jpa.repository.notice_vote;

import com.dungi.core.domain.common.DeleteStatus;
import com.dungi.core.domain.notice_vote.dto.GetNoticeVoteDto;
import com.dungi.core.domain.notice_vote.model.NoticeVote;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeVoteJpaRepository extends CrudRepository<NoticeVote, Long> {
    @Query(value = "SELECT new com.dungi.core.domain.notice_vote.dto.GetNoticeVoteDto(" +
            " nv.type," +
            " nv.noticeVoteId," +
            " u.profileImg," +
            " nv.content," +
            " nv.userId," +
            " nv.createdTime" +
            " ) " +
            " FROM NoticeVote nv" +
            " INNER JOIN User u ON nv.userId=u.id" +
            " WHERE nv.roomId=:roomId AND nv.deleteStatus=:status")
    List<GetNoticeVoteDto> findAllNoticeVote(
            @Param("roomId") Long roomId,
            @Param("status") DeleteStatus status,
            Pageable pageable
    );
}
