package com.project.dungi.domain.room.service;

import com.project.dungi.domain.room.dto.GetRoomUserDto;
import com.project.dungi.domain.room.model.Room;

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
