package com.project.dungi.infrastructure.jpa.notice_vote;

import com.project.dungi.domain.common.DeleteStatus;
import com.project.dungi.domain.notice.model.Notice;
import com.project.dungi.domain.notice_vote.dto.GetNoticeVoteDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoticeVoteRepository extends CrudRepository<Notice, Long> {
    @Query(value = "select nv.type, nv.id, u.profile_img as profileImg, nv.content, u.users_id as userId, nv.created_time as createdAt " +
            " from (" +
            " (select vote_id as id, user_id as users_id, title as content, created_time, 'V' as type " +
            " from Vote v " +
            " where v.room_id=:roomId and v.delete_status=:#{#status?.name()}) " +
            " union " +
            " (select notice_id as id, users_id, notice_item as content, created_time, 'N' as type " +
            " from Notice n " +
            " where n.room_id=:roomId and n.delete_status=:#{#status?.name()} )) nv " +
            " inner join Users u on u.users_id = nv.users_id " +
            " order by nv.created_time desc ",
            nativeQuery = true,
            countQuery = "select nv.type, nv.id, u.profile_img as profileImg, nv.content, u.users_id as userId, nv.created_time as createdAt " +
                    " from ( " +
                    " (select vote_id as id, user_id as users_id, title as content, created_time, 'V' as type " +
                    " from Vote v " +
                    " where v.room_id=:roomId and v.delete_status=:#{#status?.name()}) " +
                    " union " +
                    " (select notice_id as id, users_id, notice_item as content, created_time, 'N' as type " +
                    " from Notice n " +
                    " where n.room_id=:roomId and n.delete_status=:#{#status?.name()} )) nv " +
                    " inner join Users u on u.users_id = nv.users_id " +
                    " order by nv.created_time desc ")
    List<GetNoticeVoteDto> findAllNoticeVote(
            @Param("roomId") Long roomId,
            @Param("status") DeleteStatus status,
            Pageable pageable
    );
}
