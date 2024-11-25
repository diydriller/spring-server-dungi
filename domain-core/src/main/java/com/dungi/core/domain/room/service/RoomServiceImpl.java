package com.dungi.core.domain.room.service;

import com.dungi.core.domain.room.dto.GetRoomDto;
import com.dungi.core.integration.store.room.RoomStore;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {
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
    public GetRoomDto getAllRoomInfo(Long userId, int page, int size) {
        var roomList = roomStore.getAllRoomEnteredByUser(userId, page, size);

        List<GetRoomDto.RoomInfo> roomInfoList = new ArrayList<>();
        for (var room : roomList) {
            var memberInfoList = roomStore.getAllMemberInfo(room);
            var roomInfo = GetRoomDto.RoomInfo.builder()
                    .roomId(room.getId())
                    .roomColor(room.getColor())
                    .roomName(room.getName())
                    .members(memberInfoList)
                    .build();
            roomInfoList.add(roomInfo);
        }
        return new GetRoomDto(roomInfoList);
    }
}
