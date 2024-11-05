package com.dungi.jpa.store.room;

import com.dungi.common.exception.BaseException;
import com.dungi.common.response.BaseResponseStatus;
import com.dungi.core.domain.common.DeleteStatus;
import com.dungi.core.domain.room.dto.GetRoomUserDto;
import com.dungi.core.domain.room.model.Room;
import com.dungi.core.domain.room.model.UserRoom;
import com.dungi.core.domain.user.model.User;
import com.dungi.core.infrastructure.store.room.RoomStore;
import com.dungi.jpa.repository.room.RoomJpaRepository;
import com.dungi.jpa.repository.room.UserRoomJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RoomStoreImpl implements RoomStore {
    private final UserRoomJpaRepository userRoomJpaRepository;
    private final RoomJpaRepository roomJpaRepository;

    @Override
    public Room getRoomEnteredByUser(Long userId, Long roomId) {
        return userRoomJpaRepository.getRoomEnteredByUser(userId, roomId, DeleteStatus.NOT_DELETED)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXIST_USER_ROOM));
    }

    @Override
    public void saveRoom(Long userId, String roomName, String roomColor) {
        var room = new Room(roomName, roomColor);
        var userRoom = new UserRoom(userId, room);
        userRoomJpaRepository.save(userRoom);
        roomJpaRepository.save(room);
    }

    // 이전에 퇴장한 유저라면 방에 유저를 다시 넣어준다.
    @Override
    public void enterRoom(Long userId, Room room) {
        userRoomJpaRepository.getUserRoom(userId, room, DeleteStatus.NOT_DELETED)
                .ifPresentOrElse(
                        UserRoom::enter,
                        () -> {
                            var userRoom = new UserRoom(userId, room);
                            userRoomJpaRepository.save(userRoom);
                        }
                );
    }

    @Override
    public Room getRoom(Long roomId) {
        return roomJpaRepository.getRoom(roomId, DeleteStatus.NOT_DELETED)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXIST_ROOM));
    }

    // 방에 유저가 없으면 방을 삭제한다.
    @Override
    public void leaveRoom(Long userId, Room room) {
        var userRoom = userRoomJpaRepository.getUserRoom(userId, room, DeleteStatus.NOT_DELETED)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXIST_USER_ROOM));
        userRoom.leave();
        userRoomJpaRepository.save(userRoom);

        int num = userRoomJpaRepository.countRoomMember(room, DeleteStatus.NOT_DELETED);
        if (num <= 0) {
            room.deactivate();
            roomJpaRepository.save(room);
        }
    }

    @Override
    public List<Room> getAllRoomEnteredByUser(Long userId, int page, int size) {
        var pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "createdTime");
        return roomJpaRepository.getAllRoomEnteredByUser(
                userId,
                DeleteStatus.NOT_DELETED,
                pageRequest
        );
    }

    @Override
    public int getRoomMemberCnt(Long roomId) {
        return roomJpaRepository.getRoomMemberCnt(roomId, DeleteStatus.NOT_DELETED);
    }

    @Override
    public List<GetRoomUserDto> getAllMemberInfo(Room room) {
        return roomJpaRepository.getAllMemberInfo(room);
    }

    @Override
    public List<User> getAllMemberInRoom(Room room) {
        return userRoomJpaRepository.findAllMemberByRoomId(room.getId(), DeleteStatus.NOT_DELETED);
    }
}
