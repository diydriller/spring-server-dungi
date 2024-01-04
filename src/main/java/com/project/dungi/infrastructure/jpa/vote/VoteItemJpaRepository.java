package com.project.dungi.infrastructure.jpa.vote;

import com.project.dungi.domain.common.DeleteStatus;
import com.project.dungi.domain.user.model.User;
import com.project.dungi.domain.vote.model.Vote;
import com.project.dungi.domain.vote.model.VoteItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VoteItemJpaRepository extends CrudRepository<VoteItem,Long> {

    @Query("SELECT vi" +
            " FROM VoteItem vi" +
            " WHERE vi.vote=:vote")
    List<VoteItem> getVoteItemList(
            @Param("vote") Vote vote
    );

    @Query("SELECT vi " +
            " FROM VoteItem vi" +
            " WHERE vi.id=:choiceId ")
    Optional<VoteItem> findById(
            @Param("choiceId") Long choiceId
    );
}
