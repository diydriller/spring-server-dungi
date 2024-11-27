package com.dungi.apiserver.application.room.service;

import com.dungi.core.domain.room.query.RoomDetail;
import com.dungi.core.integration.store.room.RoomStore;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RoomService {
    private final RoomStore roomStore;

    // 방 생성 기능
    // 방 저장
    @Transactional
    public void createRoom(String roomName, String roomColor, Long userId) {
        roomStore.saveRoom(userId, roomName, roomColor);
    }

    // 방 입장 기능
    // 방 유무 확인 - 방에 유저 저장
    @Transactional
    public void enterRoom(Long userId, Long roomId) {
        var room = roomStore.getRoom(roomId);
        roomStore.enterRoom(userId, room);
    }

    // 방 퇴장 기능
    // 방에 유저 있는지 확인 - 방에서 유저 삭제
    @Transactional
    public void leaveRoom(Long userId, Long roomId) {
        var room = roomStore.getRoomEnteredByUser(userId, roomId);
        roomStore.leaveRoom(userId, room);
    }

    // 방 조회 기능
    // 방 조회 - 멤버 정보 조회
    @Transactional(readOnly = true)
    public List<RoomDetail> getAllRoomInfo(Long userId, int page, int size) {
        var roomList = roomStore.getAllRoomEnteredByUser(userId, page, size);

        List<RoomDetail> roomDetailList = new ArrayList<>();
        for (var room : roomList) {
            var memberInfoList = roomStore.getAllMemberInfo(room);
            var roomInfo = RoomDetail.builder()
                    .roomId(room.getId())
                    .roomColor(room.getColor())
                    .roomName(room.getName())
                    .members(memberInfoList)
                    .build();
            roomDetailList.add(roomInfo);
        }
        return roomDetailList;
    }
}
