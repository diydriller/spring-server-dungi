package com.dungi.rdb.dto.room;

import com.dungi.core.domain.common.query.UserDetail;
import com.dungi.core.domain.room.query.RoomDetail;
import com.dungi.core.domain.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetRoomUserDto {
    private String profileImg;
    private String nickname;

    public static UserDetail createRoomUser(GetRoomUserDto dto) {
        return UserDetail.builder()
                .nickname(dto.getNickname())
                .profileImg(dto.getProfileImg())
                .build();
    }
}
