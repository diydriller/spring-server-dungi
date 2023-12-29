package com.project.dungi.application.todo.controller;

import com.project.dungi.application.todo.dto.CreateRepeatTodoRequestDto;
import com.project.dungi.application.todo.dto.CreateTodayTodoRequestDto;
import com.project.dungi.application.todo.dto.GetRepeatTodoResponseDto;
import com.project.dungi.application.todo.dto.GetTodayTodoResponseDto;
import com.project.dungi.common.exception.AuthenticationException;
import com.project.dungi.common.response.BaseResponse;
import com.project.dungi.common.util.TimeUtil;
import com.project.dungi.domain.todo.service.TodoService;
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

@Slf4j
@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/room/{roomId}/todo/day")
    BaseResponse<?> createTodayTodo(
            @PathVariable Long roomId,
            @RequestBody @Valid CreateTodayTodoRequestDto todoRequestDto,
            HttpSession session
    ){
        var user = Optional.ofNullable(session.getAttribute(LOGIN_USER))
                .map(o -> (User)o)
                .orElseThrow(() -> new AuthenticationException(AUTHENTICATION_ERROR));
            todoService.createTodayTodo(todoRequestDto.getTodo(), todoRequestDto.getTime(), user.getId(), roomId);
            return new BaseResponse<>(SUCCESS);
    }

    @PostMapping("/room/{roomId}/todo/days")
    BaseResponse<?> createRepeatTodo(
            @PathVariable Long roomId,
            @RequestBody @Valid CreateRepeatTodoRequestDto requestDto,
            HttpSession session
    ) {
        User user = Optional.ofNullable(session.getAttribute(LOGIN_USER))
                .map(o -> (User)o)
                .orElseThrow(() -> new AuthenticationException(AUTHENTICATION_ERROR));
            todoService.createRepeatTodo(
                    requestDto.getTodo(),
                    requestDto.getTime(),
                    requestDto.getDays(),
                    user.getId(),
                    roomId
            );
            return new BaseResponse<>(SUCCESS);
    }

    @GetMapping("/room/{roomId}/todo/day")
    BaseResponse<?> getTodayTodo(
            @PathVariable Long roomId,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            HttpSession session
    ){
        User user = Optional.ofNullable(session.getAttribute(LOGIN_USER))
                .map(o -> (User)o)
                .orElseThrow(() -> new AuthenticationException(AUTHENTICATION_ERROR));
        var todayTodo = todoService.getTodayTodo(user.getId(), roomId, page, size).stream()
                .map(t -> GetTodayTodoResponseDto.builder()
                        .todo(t.getTodoItem())
                        .todoId(t.getId())
                        .isOwner(t.getUserId().equals(user.getId()))
                        .deadline(TimeUtil.localDateTimeToTimeStr(t.getDeadline()))
                        .build())
                .collect(Collectors.toList());
            return new BaseResponse<>(todayTodo);
    }

    @GetMapping("/room/{roomId}/todo/days")
    BaseResponse<?> getRepeatTodo(
            @PathVariable Long roomId,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            HttpSession session
    ){
        User user = Optional.ofNullable(session.getAttribute(LOGIN_USER))
                .map(o -> (User)o)
                .orElseThrow(() -> new AuthenticationException(AUTHENTICATION_ERROR));

        var repeatTodoList = todoService.getRepeatTodo(user.getId(), roomId, page, size).stream()
                .map(t -> GetRepeatTodoResponseDto.builder()
                        .todoId(t.getTodoId())
                        .todo(t.getTodo())
                        .deadline(t.getDeadline().getHour() + "/" + t.getDeadline().getMinute())
                        .isOwner(t.getUserId().equals(user.getId()))
                        .day(t.getDay())
                        .build()
                ).collect(Collectors.toList());
            return new BaseResponse<>(repeatTodoList);
    }
}
