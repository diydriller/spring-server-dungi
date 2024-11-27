package com.dungi.core.domain.room.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Builder
@AllArgsConstructor
public class RoomDetail {
    private Long roomId;
    private String roomName;
    private String roomColor;
    @Builder.Default
    private List<RoomUser> roomUserList = new ArrayList<>();

    @Getter
    @Builder
    @AllArgsConstructor
    public static class RoomUser {
        private Long userId;
        private String profileImg;
        private String nickname;
    }
}

