package com.dungi.rdb.dto.room;

import com.dungi.core.domain.room.query.RoomDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetRoomUserDto {
    private String profileImg;
    private String nickname;

    public static RoomDetail.RoomUser createRoomUser(GetRoomUserDto dto) {
        return RoomDetail.RoomUser.builder()
                .nickname(dto.getNickname())
                .profileImg(dto.getProfileImg())
                .build();
    }
}
