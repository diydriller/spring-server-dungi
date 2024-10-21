package com.dungi.jpa.repository.room;

import com.dungi.core.domain.common.DeleteStatus;
import com.dungi.core.domain.room.model.Room;
import com.dungi.core.domain.room.model.UserRoom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRoomJpaRepository extends CrudRepository<UserRoom, Long> {
    @Query("SELECT ur " +
            " FROM UserRoom ur" +
            " WHERE ur.room=:room AND ur.userId=:userId AND ur.deleteStatus=:status")
    Optional<UserRoom> getUserRoom(
            @Param("userId") Long userId,
            @Param("room") Room room,
            @Param("status") DeleteStatus status
    );

    @Query("SELECT ur.room " +
            " FROM UserRoom ur JOIN ur.room r " +
            " WHERE ur.userId=:userId AND r.id=:roomId AND r.deleteStatus=:status AND ur.deleteStatus=:status")
    Optional<Room> getRoomEnteredByUser(
            @Param("userId") Long userId,
            @Param("roomId") Long roomId,
            @Param("status") DeleteStatus status
    );

    @Query("SELECT COUNT(ur) " +
            " FROM UserRoom ur " +
            " WHERE ur.room=:room AND ur.deleteStatus=:status")
    int countRoomMember(
            @Param("room") Room room,
            @Param("status") DeleteStatus status
    );

    @Query("SELECT ur.userId " +
            " FROM UserRoom ur " +
            " INNER JOIN User u ON ur.userId=u.id" +
            " WHERE ur.room=:room AND ur.deleteStatus=:status AND u.deleteStatus=:status")
    List<Long> findAllMemberId(
            @Param("room") Room room,
            @Param("status") DeleteStatus status);
}
