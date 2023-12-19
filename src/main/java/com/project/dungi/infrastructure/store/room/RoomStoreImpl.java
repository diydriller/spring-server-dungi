package com.project.dungi.infrastructure.store.room;

import com.project.dungi.common.exception.BaseException;
import com.project.dungi.domain.common.DeleteStatus;
import com.project.dungi.domain.room.dto.GetRoomUserDto;
import com.project.dungi.domain.room.model.Room;
import com.project.dungi.domain.room.model.UserRoom;
import com.project.dungi.domain.room.service.RoomStore;
import com.project.dungi.infrastructure.jpa.room.RoomRepository;
import com.project.dungi.infrastructure.jpa.room.UserRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.project.dungi.common.response.BaseResponseStatus.NOT_EXIST_ROOM;
import static com.project.dungi.common.response.BaseResponseStatus.NOT_EXIST_USER_ROOM;

@Component
@RequiredArgsConstructor
public class RoomStoreImpl implements RoomStore {

    private final UserRoomRepository userRoomRepository;
    private final RoomRepository roomRepository;

    @Override
    public Room getRoomEnteredByUser(Long userId, Long roomId) {
        return userRoomRepository.getRoomEnteredByUser(userId, roomId, DeleteStatus.NOT_DELETED)
                .orElseThrow(()->{
                    throw new BaseException(NOT_EXIST_USER_ROOM);
                });
    }

    @Override
    public void saveRoom(Long userId, String roomName, String roomColor) {
        var room = new Room(roomName, roomColor);
        var userRoom = new UserRoom(userId, room);
        userRoomRepository.save(userRoom);
        roomRepository.save(room);
    }

    // 이전에 퇴장한 유저라면 방에 유저를 다시 넣어준다.
    @Override
    public void enterRoom(Long userId, Room room) {
        userRoomRepository.getUserRoom(userId, room, DeleteStatus.NOT_DELETED)
                .ifPresentOrElse(
                        ur -> ur.enter(),
                        ()->{
                            var userRoom = new UserRoom(userId, room);
                            userRoomRepository.save(userRoom);
                        }
                );
    }

    @Override
    public Room getRoom(Long roomId) {
        return roomRepository.getRoom(roomId, DeleteStatus.NOT_DELETED)
                .orElseThrow(() -> {
                    throw new BaseException(NOT_EXIST_ROOM);
                });
    }

    // 방에 유저가 없으면 방을 삭제한다.
    @Override
    public void leaveRoom(Long userId, Room room) {
        var userRoom = userRoomRepository.getUserRoom(userId, room, DeleteStatus.NOT_DELETED)
                .orElseThrow(()->{
                    throw new BaseException(NOT_EXIST_USER_ROOM);
                });
        userRoom.leave();
        userRoomRepository.save(userRoom);

        int num = userRoomRepository.countRoomMember(room, DeleteStatus.NOT_DELETED);
        if(num <= 0){
            room.deactivate();
            roomRepository.save(room);
        }
    }

    @Override
    public List<Room> getAllRoomEnteredByUser(Long userId, int page, int size) {
        var pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "createdTime");
        return roomRepository.getAllRoomEnteredByUser(
                userId,
                DeleteStatus.NOT_DELETED,
                pageRequest
        );
    }

    @Override
    public int getRoomMemberCnt(Long roomId) {
        return roomRepository.getRoomMemberCnt(roomId, DeleteStatus.NOT_DELETED);
    }

    @Override
    public List<GetRoomUserDto> getAllMemberInfo(Room room) {
        return roomRepository.getAllMemberInfo(room);
    }
}
