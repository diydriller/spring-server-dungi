package com.dungi.apiserver.application.notice_vote.controller;

import com.dungi.apiserver.application.notice.dto.GetNoticeVoteResponseDto;
import com.dungi.common.exception.AuthenticationException;
import com.dungi.common.response.BaseResponse;
import com.dungi.common.util.TimeUtil;
import com.dungi.core.domain.notice_vote.service.NoticeVoteService;
import com.dungi.core.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.dungi.common.response.BaseResponseStatus.AUTHENTICATION_ERROR;
import static com.dungi.common.util.StringUtil.*;

@RestController
@RequiredArgsConstructor
public class NoticeVoteController {

    private final NoticeVoteService noticeVoteService;

    @GetMapping(value = "/room/{roomId}/noticeVote")
    public BaseResponse<?> getNoticeVote(
            @PathVariable Long roomId,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            HttpSession session
    ) {
        var user = Optional.ofNullable(session.getAttribute(LOGIN_USER))
                .map(o->(User)o).orElseThrow(()->new AuthenticationException(AUTHENTICATION_ERROR));

        var noticeVoteList = noticeVoteService.getNoticeVote(roomId, user.getId(), page, size).stream()
                .map(nv -> GetNoticeVoteResponseDto.builder()
                        .id(nv.getId())
                        .profileImg(nv.getProfileImg())
                        .userId(nv.getUserId())
                        .isOwner(nv.getUserId().equals(user.getId()) ? "Y" : "N")
                        .createdAt(TimeUtil.localDateTimeToTimeStr(nv.getCreatedAt()))
                        .isNotice(nv.getType())
                        .title(nv.getType().equals(VOTE_TYPE) ? nv.getContent() : null)
                        .notice(nv.getType().equals(NOTICE_TYPE) ? nv.getContent() : null)
                        .build()
                ).collect(Collectors.toList());
        return new BaseResponse<>(noticeVoteList);
    }
}
