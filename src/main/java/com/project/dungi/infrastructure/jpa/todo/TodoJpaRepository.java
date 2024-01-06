package com.project.dungi.infrastructure.jpa.todo;

import com.project.dungi.domain.common.DeleteStatus;
import com.project.dungi.domain.common.FinishStatus;
import com.project.dungi.domain.todo.model.RepeatTodo;
import com.project.dungi.domain.todo.model.TodayTodo;
import com.project.dungi.domain.todo.model.Todo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TodoJpaRepository extends CrudRepository<Todo,Long> {

    Optional<Todo> findById(Long id);

    @Query(value = "SELECT tt " +
            " FROM TodayTodo tt" +
            " WHERE tt.roomId=:roomId " +
            " AND tt.deleteStatus=:deleteStatus " +
            " AND tt.finishStatus=:finishStatus " +
            " AND tt.deadline BETWEEN :currentTime AND :todayLastTime",
    countQuery = "SELECT COUNT(tt.id) FROM TodayTodo tt")
    List<TodayTodo> findAllPossibleTodayTodo(
            @Param("roomId") Long roomId,
            @Param("deleteStatus") DeleteStatus deleteStatus,
            @Param("finishStatus") FinishStatus finishStatus,
            @Param("currentTime") LocalDateTime currentTime,
            @Param("todayLastTime") LocalDateTime todayLastTime,
            Pageable pageable
    );


    @Query(value="SELECT rt " +
            " FROM RepeatTodo rt" +
            " WHERE rt.roomId=:roomId AND rt.deleteStatus=:status",
    countQuery = "SELECT COUNT(rt.id) FROM RepeatTodo rt")
    List<RepeatTodo> findAllPossibleRepeatTodo(
            @Param("roomId") Long roomId,
            @Param("status") DeleteStatus status,
            Pageable pageable
    );
}
