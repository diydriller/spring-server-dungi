package com.dungi.core.domain.room.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class GetRoomDto {

    private List<GetRoomDto.RoomInfo> roomInfo;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class RoomInfo{
        private Long roomId;
        private String roomName;
        private String roomColor;
        @Builder.Default
        private List<GetRoomUserDto> members = new ArrayList<>();
    }
}
