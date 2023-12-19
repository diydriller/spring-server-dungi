package com.project.dungi.infrastructure.jpa.vote;

import com.project.dungi.domain.common.DeleteStatus;
import com.project.dungi.domain.vote.dto.VoteUserDto;
import com.project.dungi.domain.vote.model.UserVoteItem;
import com.project.dungi.domain.vote.model.VoteItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserVoteItemRepository extends CrudRepository<UserVoteItem,Long> {

    @Query("SELECT new com.project.dungi.domain.vote.dto.VoteUserDto(" +
            " u.profileImg," +
            " u.nickname," +
            " u.id" +
            " )" +
            " FROM UserVoteItem uvi INNER JOIN User u ON uvi.userId=u.id" +
            " WHERE uvi.voteItem.id=:voteItemId AND uvi.deleteStatus=:status")
    List<VoteUserDto> getVoteUser(
            @Param("voteItemId") Long voteItemId,
            @Param("status") DeleteStatus status
    );

    @Query("SELECT uvi " +
            " FROM UserVoteItem uvi " +
            " WHERE uvi.userId=:userId AND uvi.voteItem=:voteItem")
    Optional<UserVoteItem> findByUserAndVoteItem(
            @Param("userId") Long userId,
            @Param("voteItem") VoteItem voteItem
    );
}
