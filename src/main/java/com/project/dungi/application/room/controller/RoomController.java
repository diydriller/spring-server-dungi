package com.project.dungi.application.room.controller;

import com.project.dungi.application.room.dto.CreateRoomRequestDto;
import com.project.dungi.application.room.dto.GetRoomResponseDto;
import com.project.dungi.common.exception.AuthenticationException;
import com.project.dungi.common.response.BaseResponse;
import com.project.dungi.domain.room.service.RoomService;
import com.project.dungi.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.project.dungi.common.response.BaseResponseStatus.AUTHENTICATION_ERROR;
import static com.project.dungi.common.response.BaseResponseStatus.SUCCESS;
import static com.project.dungi.common.util.StringUtil.LOGIN_USER;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/room")
    BaseResponse<?> createRoom(
            @RequestBody @Valid CreateRoomRequestDto requestDto,
            HttpSession session
    ) {
        User user = Optional.ofNullable(session.getAttribute(LOGIN_USER))
                .map(o->(User)o).orElseThrow(() -> new AuthenticationException(AUTHENTICATION_ERROR));
        roomService.createRoom(requestDto.getName(), requestDto.getColor(), user.getId());
        return new BaseResponse<>(SUCCESS);
    }

    @PostMapping("/room/{roomId}/member")
    BaseResponse<?> enterRoom(
            @PathVariable Long roomId,
            HttpSession session
    ){
        User user = Optional.ofNullable(session.getAttribute(LOGIN_USER))
                .map(o->(User)o).orElseThrow(() -> new AuthenticationException(AUTHENTICATION_ERROR));
        roomService.enterRoom(user.getId(), roomId);
        return new BaseResponse<>(SUCCESS);
    }

    @DeleteMapping("/room/{roomId}/member")
    BaseResponse<?> leaveRoom(
            @PathVariable Long roomId,
            HttpSession session
    ){
        User user = Optional.ofNullable(session.getAttribute(LOGIN_USER))
                .map(o->(User)o).orElseThrow(() -> new AuthenticationException(AUTHENTICATION_ERROR));
            roomService.leaveRoom(user.getId(), roomId);
            return new BaseResponse<>(SUCCESS);
    }


    @GetMapping("/room")
    BaseResponse<GetRoomResponseDto> getRoom(
            @RequestParam int page,
            @RequestParam int size,
            HttpSession session
    ) {
        User user = Optional.ofNullable(session.getAttribute(LOGIN_USER))
                .map(o->(User)o).orElseThrow(() -> new AuthenticationException(AUTHENTICATION_ERROR));
        var roomInfoDto = roomService.getAllRoomInfo(user.getId(), page, size);
        var roomInfoRes = new GetRoomResponseDto(
                roomInfoDto.getRoomInfo().stream()
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
                ,user.getNickname());
        return new BaseResponse<>(roomInfoRes);
    }
}
