package com.dungi.apiserver.application.vote.controller;

import com.dungi.apiserver.application.vote.dto.CreateVoteRequestDto;
import com.dungi.apiserver.application.vote.dto.GetVoteItemResponseDto;
import com.dungi.common.exception.AuthenticationException;
import com.dungi.common.response.BaseResponse;
import com.dungi.core.domain.user.model.User;
import com.dungi.core.domain.vote.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.dungi.common.response.BaseResponseStatus.AUTHENTICATION_ERROR;
import static com.dungi.common.response.BaseResponseStatus.SUCCESS;
import static com.dungi.common.util.StringUtil.LOGIN_USER;

@RequiredArgsConstructor
@RestController
public class VoteController {
    private final VoteService voteService;

    @PostMapping(value = "/room/{roomId}/vote")
    public BaseResponse<?> createVote(
            @PathVariable Long roomId,
            @RequestBody @Valid CreateVoteRequestDto requestDto,
            HttpSession session
    ){
        var user = Optional.ofNullable(session.getAttribute(LOGIN_USER))
                .map(o -> (User)o)
                .orElseThrow(() -> new AuthenticationException(AUTHENTICATION_ERROR));
        voteService.createVote(requestDto.getTitle(), requestDto.getChoiceArr(), user.getId(), roomId);
        return new BaseResponse<>(SUCCESS);
    }

    @GetMapping(value = "/room/{roomId}/vote/{voteId}")
    public BaseResponse<?> getVote(
            @PathVariable Long roomId,
            @PathVariable Long voteId,
            HttpSession session
    )  {
        var user = Optional.ofNullable(session.getAttribute(LOGIN_USER))
                .map(o -> (User)o)
                .orElseThrow(() -> new AuthenticationException(AUTHENTICATION_ERROR));
        var getVoteItemDto = voteService.getVote(roomId, user.getId(), voteId);
        var getVoteItem = GetVoteItemResponseDto.builder()
                .choice(getVoteItemDto.getChoice().stream()
                        .map(vc -> new GetVoteItemResponseDto.VoteChoiceDto(
                                vc.getChoice(),
                                vc.getVoteUser().stream()
                                        .map(vu -> new GetVoteItemResponseDto.VoteUserDto(
                                                vu.getProfileImg(),
                                                vu.getNickname()
                                        ))
                                        .collect(Collectors.toList())))
                        .collect(Collectors.toList()))
                .choiceIdList(getVoteItemDto.getChoiceIdList())
                .isFinished(getVoteItemDto.getIsFinished())
                .isOwner(getVoteItemDto.getIsOwner())
                .unVotedMemberCnt(getVoteItemDto.getUnVotedMemberCnt())
                .title(getVoteItemDto.getTitle())
                .build();
        return new BaseResponse<>(getVoteItem);
    }

    @PatchMapping("/room/{roomId}/vote/{voteId}/choice/{choiceId}")
    public BaseResponse<?> createVoteChoice(
            @PathVariable  Long roomId,
            @PathVariable Long voteId,
            @PathVariable Long choiceId,HttpSession session
    ) {
        var user = Optional.ofNullable(session.getAttribute(LOGIN_USER))
                .map(o -> (User)o)
                .orElseThrow(() -> new AuthenticationException(AUTHENTICATION_ERROR));
        voteService.createVoteChoice(roomId, user.getId(), voteId, choiceId);
        return new BaseResponse<>(SUCCESS);
    }
}
