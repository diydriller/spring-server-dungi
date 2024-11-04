package com.dungi.apiserver.application.todo.controller;

import com.dungi.apiserver.application.todo.dto.CreateRepeatTodoRequestDto;
import com.dungi.apiserver.application.todo.dto.CreateTodayTodoRequestDto;
import com.dungi.apiserver.application.todo.dto.GetRepeatTodoResponseDto;
import com.dungi.apiserver.application.todo.dto.GetTodayTodoResponseDto;
import com.dungi.common.response.BaseResponse;
import com.dungi.common.util.TimeUtil;
import com.dungi.core.domain.todo.service.TodoService;
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
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/room/{roomId}/todo/day")
    BaseResponse<?> createTodayTodo(
            @PathVariable Long roomId,
            @RequestBody @Valid CreateTodayTodoRequestDto todoRequestDto,
            HttpSession session
    ) {
        var user = (User) session.getAttribute(LOGIN_USER);
        todoService.createTodayTodo(todoRequestDto.getTodo(), todoRequestDto.getTime(), user.getId(), roomId);
        return new BaseResponse<>(SUCCESS);
    }

    @PostMapping("/room/{roomId}/todo/days")
    BaseResponse<?> createRepeatTodo(
            @PathVariable Long roomId,
            @RequestBody @Valid CreateRepeatTodoRequestDto requestDto,
            HttpSession session
    ) {
        var user = (User) session.getAttribute(LOGIN_USER);
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
    ) {
        var user = (User) session.getAttribute(LOGIN_USER);
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
    ) {
        var user = (User) session.getAttribute(LOGIN_USER);

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

    @PatchMapping("/room/{roomId}/todo/{todoId}/day")
    BaseResponse<?> completeTodayTodo(
            @PathVariable Long roomId,
            @PathVariable Long todoId,
            HttpSession session
    ){
        var user = (User) session.getAttribute(LOGIN_USER);

        todoService.completeTodayTodo(user.getId(), roomId, todoId);
        return new BaseResponse<>(SUCCESS);
    }
}
