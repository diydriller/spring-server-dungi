package com.project.dungi.application.notice.controller;

import com.project.dungi.application.notice.dto.CreateNoticeRequestDto;
import com.project.dungi.common.exception.AuthenticationException;
import com.project.dungi.common.response.BaseResponse;
import com.project.dungi.domain.notice.service.NoticeService;
import com.project.dungi.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

import static com.project.dungi.common.response.BaseResponseStatus.AUTHENTICATION_ERROR;
import static com.project.dungi.common.response.BaseResponseStatus.SUCCESS;
import static com.project.dungi.common.util.StringUtil.LOGIN_USER;

@RestController
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping(value = "/room/{roomId}/notice")
    public BaseResponse<?> createNotice(
            @PathVariable Long roomId,
            @RequestBody @Valid CreateNoticeRequestDto noticeRequestDto,
            HttpSession session
    ) {
        User user = Optional.ofNullable(session.getAttribute(LOGIN_USER))
                .map(o -> (User)o)
                .orElseThrow(() -> new AuthenticationException(AUTHENTICATION_ERROR));
        noticeService.createNotice(noticeRequestDto.getNotice(), user.getId(), roomId);
        return new BaseResponse<>(SUCCESS);
    }
}
