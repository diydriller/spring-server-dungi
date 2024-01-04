package com.project.dungi.application.memo.controller;

import com.project.dungi.application.memo.dto.CreateMemoRequestDto;
import com.project.dungi.application.memo.dto.GetMemoResponseDto;
import com.project.dungi.application.memo.dto.MoveMemoRequestDto;
import com.project.dungi.application.memo.dto.UpdateMemoRequestDto;
import com.project.dungi.common.exception.AuthenticationException;
import com.project.dungi.common.response.BaseResponse;
import com.project.dungi.common.util.TimeUtil;
import com.project.dungi.domain.memo.service.MemoService;
import com.project.dungi.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

import static com.project.dungi.common.response.BaseResponseStatus.AUTHENTICATION_ERROR;
import static com.project.dungi.common.response.BaseResponseStatus.SUCCESS;
import static com.project.dungi.common.util.StringUtil.LOGIN_USER;

@RestController
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    @PostMapping("/room/{roomId}/memo")
    BaseResponse<?> createMemo(
            @PathVariable Long roomId,
            @RequestBody @Valid CreateMemoRequestDto memoRequestDto,
            HttpSession session
    ) throws Exception {
        User user = Optional.ofNullable(session.getAttribute(LOGIN_USER))
                .map(o -> (User)o)
                .orElseThrow(() -> new AuthenticationException(AUTHENTICATION_ERROR));
        memoService.createMemo(memoRequestDto.getMemo(),
                memoRequestDto.getMemoColor(),
                memoRequestDto.getX(),
                memoRequestDto.getY(),
                user.getId(),
                roomId
        );
        return new BaseResponse<>(SUCCESS);
    }

    @GetMapping("/room/{roomId}/memo")
    BaseResponse<?> getMemo(
            @PathVariable Long roomId,
            HttpSession session
    ) throws Exception {
        User user = Optional.ofNullable(session.getAttribute(LOGIN_USER))
                .map(o -> (User)o)
                .orElseThrow(() -> new AuthenticationException(AUTHENTICATION_ERROR));
        var memoList = memoService.getMemo(roomId, user.getId()).stream()
                .map(m -> GetMemoResponseDto.builder()
                        .memoId(m.getId())
                        .memoColor(m.getMemoColor())
                        .profileImg(m.getProfileImg())
                        .createdAt(TimeUtil.localDateTimeToTimeStr(m.getCreatedTime()))
                        .isOwner(m.getUserId().equals(user.getId()))
                        .memo(m.getMemoItem())
                        .x(m.getXPosition())
                        .y(m.getYPosition())
                        .build()
                );
        return new BaseResponse<>(memoList);
    }

    @PutMapping("/room/{roomId}/memo/{memoId}/update")
    BaseResponse<?> updateMemo(
            @PathVariable Long roomId,
            @PathVariable Long memoId,
            @RequestBody @Valid UpdateMemoRequestDto memoRequestDto,
            HttpSession session
    ) {
        User user = Optional.ofNullable(session.getAttribute(LOGIN_USER))
                .map(o -> (User)o)
                .orElseThrow(() -> new AuthenticationException(AUTHENTICATION_ERROR));
        memoService.updateMemo(memoRequestDto.getMemo(),
                memoRequestDto.getMemoColor(),
                user.getId(),
                roomId,
                memoId
        );
        return new BaseResponse<>(SUCCESS);
    }

    @PutMapping("/room/{roomId}/memo/{memoId}/move")
    BaseResponse<?> moveMemo(
            @PathVariable Long roomId,
            @PathVariable Long memoId,
            @RequestBody @Valid MoveMemoRequestDto memoRequestDto,
            HttpSession session
    ) {
        User user = Optional.ofNullable(session.getAttribute(LOGIN_USER))
                .map(o -> (User)o)
                .orElseThrow(() -> new AuthenticationException(AUTHENTICATION_ERROR));
        memoService.moveMemo(memoRequestDto.getX(),
                memoRequestDto.getY(),
                user.getId(),
                roomId,
                memoId
        );
        return new BaseResponse<>(SUCCESS);
    }

    @DeleteMapping("/room/{roomId}/memo/{memoId}")
    BaseResponse<?> deleteMemo(
            @PathVariable Long roomId,
            @PathVariable Long memoId,HttpSession session
    ) {
        User user = Optional.ofNullable(session.getAttribute(LOGIN_USER))
                .map(o -> (User)o)
                .orElseThrow(() -> new AuthenticationException(AUTHENTICATION_ERROR));
        memoService.deleteMemo(user.getId(), roomId, memoId);
        return new BaseResponse<>(SUCCESS);
    }
}
