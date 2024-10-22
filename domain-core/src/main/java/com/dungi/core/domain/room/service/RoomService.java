package com.dungi.core.domain.room.service;

import com.dungi.core.domain.room.dto.GetRoomDto;

public interface RoomService {
    void createRoom(String roomName, String roomColor, Long userId);

    void enterRoom(Long userId, Long roomId);

    void leaveRoom(Long userId, Long roomId);

    GetRoomDto getAllRoomInfo(Long userId, int page, int size);
}
