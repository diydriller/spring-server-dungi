package com.dungi.core.domain.room.service;

import com.dungi.core.domain.room.dto.GetRoomUserDto;
import com.dungi.core.domain.room.model.Room;

import java.util.List;

public interface RoomStore {
    Room getRoomEnteredByUser(Long userId, Long roomId);
    void saveRoom(Long userId, String roomName, String roomColor);
    void enterRoom(Long userId, Room room);
    Room getRoom(Long roomId);
    void leaveRoom(Long userId, Room room);
    List<Room> getAllRoomEnteredByUser(Long userId, int page, int size);
    int getRoomMemberCnt(Long roomId);
    List<GetRoomUserDto> getAllMemberInfo(Room room);
    List<Long> findAllMemberId(Room room);
}
