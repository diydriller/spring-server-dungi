package com.dungi.apiserver.application;

import com.dungi.apiserver.application.memo.dto.MoveMemoDto;
import com.dungi.apiserver.application.memo.service.MemoService;
import com.dungi.apiserver.config.BaseIntegrationTest;
import com.dungi.core.domain.memo.model.Memo;
import com.dungi.core.domain.room.model.Room;
import com.dungi.core.domain.room.model.UserRoom;
import com.dungi.rdb.jpa.repository.memo.MemoJpaRepository;
import com.dungi.rdb.jpa.repository.room.RoomJpaRepository;
import com.dungi.rdb.jpa.repository.room.UserRoomJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class MemoServiceIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private MemoService memoService;

    @Autowired
    private MemoJpaRepository memoJpaRepository;

    @Autowired
    private RoomJpaRepository roomJpaRepository;

    @Autowired
    private UserRoomJpaRepository userRoomJpaRepository;

    @DisplayName(value = "5명이 동시에 메모 이동시 OptimisticLockingFailureException 1개 이상 발생")
    @Test
    void whenFiveUserMoveMemoThenThrowException() {
        // given
        var room = new Room("test", "#ffffff");
        var savedRoom = roomJpaRepository.save(room);

        var userIdList = List.of(1L, 2L, 3L, 4L, 5L);

        userIdList.forEach(userId -> {
            var userRoom = new UserRoom(userId, savedRoom);
            userRoomJpaRepository.save(userRoom);
        });

        var memo = Memo.builder()
                .memoColor("#ffffff")
                .memoItem("test")
                .roomId(savedRoom.getId())
                .userId(5L)
                .xPosition(0.0)
                .yPosition(0.0)
                .build();
        var savedMemo = memoJpaRepository.save(memo);

        var moveMemoList = userIdList.stream()
                .map(userId -> new MoveMemoDto(Math.random() * 10, Math.random() * 10, savedMemo.getId(), savedRoom.getId(), userId))
                .collect(Collectors.toList());

        var executor = Executors.newFixedThreadPool(5);
        var exceptionList = new ArrayList<Exception>();

        // when
        CompletableFuture<Void> allOf = CompletableFuture.allOf(moveMemoList.stream()
                .map(moveMemo -> CompletableFuture.runAsync(() -> {
                    try {
                        memoService.moveMemo(moveMemo, moveMemo.getRoomId(), moveMemo.getUserId(), savedMemo.getId());
                    } catch (Exception e) {
                        exceptionList.add(e);
                    }
                }, executor)).toArray(CompletableFuture[]::new));
        allOf.join();

        // then
        assertThat(exceptionList.size()).isGreaterThan(0);
        exceptionList.forEach(exception -> {
            assertThat(exception instanceof OptimisticLockingFailureException).isTrue();
        });
        executor.shutdown();
    }
}
