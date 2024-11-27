package com.dungi.apiserver.presentation.room.controller;

import com.dungi.apiserver.application.room.service.RoomService;
import com.dungi.apiserver.presentation.room.dto.CreateRoomRequestDto;
import com.dungi.apiserver.presentation.room.dto.GetRoomResponseDto;
import com.dungi.common.response.BaseResponse;
import com.dungi.core.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.stream.Collectors;

import static com.dungi.common.response.BaseResponseStatus.SUCCESS;
import static com.dungi.common.util.StringUtil.LOGIN_USER;

@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/room")
    BaseResponse<?> createRoom(
            @RequestBody @Valid CreateRoomRequestDto requestDto,
            HttpSession session
    ) {
        var user = (User) session.getAttribute(LOGIN_USER);
        roomService.createRoom(requestDto.getName(), requestDto.getColor(), user.getId());
        return new BaseResponse<>(SUCCESS);
    }

    @PostMapping("/room/{roomId}/member")
    BaseResponse<?> enterRoom(
            @PathVariable Long roomId,
            HttpSession session
    ) {
        var user = (User) session.getAttribute(LOGIN_USER);
        roomService.enterRoom(user.getId(), roomId);
        return new BaseResponse<>(SUCCESS);
    }

    @DeleteMapping("/room/{roomId}/member")
    BaseResponse<?> leaveRoom(
            @PathVariable Long roomId,
            HttpSession session
    ) {
        var user = (User) session.getAttribute(LOGIN_USER);
        roomService.leaveRoom(user.getId(), roomId);
        return new BaseResponse<>(SUCCESS);
    }


    @GetMapping("/room")
    BaseResponse<GetRoomResponseDto> getRoom(
            @RequestParam int page,
            @RequestParam int size,
            HttpSession session
    ) {
        var user = (User) session.getAttribute(LOGIN_USER);
        var roomInfoDto = roomService.getAllRoomInfo(user.getId(), page, size);
        var roomInfoRes = new GetRoomResponseDto(
                roomInfoDto.stream()
                        .map(ri -> GetRoomResponseDto.RoomInfo.builder()
                                .roomColor(ri.getRoomColor())
                                .roomName(ri.getRoomName())
                                .roomId(ri.getRoomId())
                                .members(ri.getMembers().stream()
                                        .map(m -> new GetRoomResponseDto.GetRoomUserDto(
                                                m.getProfileImg(),
                                                m.getNickname())
                                        ).collect(Collectors.toList()))
                                .build()
                        ).collect(Collectors.toList())
                , user.getNickname());
        return new BaseResponse<>(roomInfoRes);
    }
}
