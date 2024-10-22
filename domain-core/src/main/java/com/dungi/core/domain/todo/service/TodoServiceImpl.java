package com.dungi.core.domain.todo.service;

import com.dungi.common.util.TimeUtil;
import com.dungi.core.domain.todo.dto.GetRepeatTodoDto;
import com.dungi.core.domain.todo.model.RepeatDay;
import com.dungi.core.domain.todo.model.RepeatTodo;
import com.dungi.core.domain.todo.model.TodayTodo;
import com.dungi.core.infrastructure.store.room.RoomStore;
import com.dungi.core.infrastructure.store.todo.TodoStore;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {
    private final TodoStore todoStore;
    private final RoomStore roomStore;

    // 오늘 할일 생성 기능
    // 유저가 방에 입장해있는지 확인 - 오늘 할일 생성
    @Transactional
    public void createTodayTodo(String todoItem, String time, Long userId, Long roomId) {
        roomStore.getRoomEnteredByUser(userId, roomId);
        var todayTodo = TodayTodo.builder()
                .todoItem(todoItem)
                .roomId(roomId)
                .userId(userId)
                .deadline(TimeUtil.timeStrToLocalDateTime(time))
                .build();
        todoStore.saveTodayTodo(todayTodo);
    }

    // 반복 할일 생성 기능
    // 유저가 방에 입장해있는지 검증 - 반복 할일 생성
    @Transactional
    public void createRepeatTodo(String todoItem, String time, String days, Long userId, Long roomId) {
        roomStore.getRoomEnteredByUser(userId, roomId);
        var repeatTodo = RepeatTodo.builder()
                .deadline(TimeUtil.timeStrToTodayLocalDateTime(time))
                .todoItem(todoItem)
                .roomId(roomId)
                .userId(userId)
                .build();
        todoStore.saveRepeatTodo(repeatTodo, dayStrToRepeatDay(days));
    }

    // 오늘 할일 조회 기능
    // 유저가 방에 입 장해있는지 검증 - 오늘 할일 조회
    @Transactional(readOnly = true)
    public List<TodayTodo> getTodayTodo(Long userId, Long roomId, int page, int size) {
        roomStore.getRoomEnteredByUser(userId, roomId);
        return todoStore.findTodayTodo(roomId, userId, page, size);
    }

    // 반복 할일 조회 기능
    // 유저가 방에 입장해있는지 검증 - 반복 할일 조회
    @Transactional(readOnly = true)
    public List<GetRepeatTodoDto> getRepeatTodo(Long userId, Long roomId, int page, int size) {
        roomStore.getRoomEnteredByUser(userId, roomId);
        return todoStore.findRepeatTodo(roomId, userId, page, size).stream()
                .map(rt -> GetRepeatTodoDto.builder()
                        .todo(rt.getTodoItem())
                        .todoId(rt.getId())
                        .deadline(rt.getDeadline())
                        .day(repeatDayTodayStr(rt.getRepeatDayList()))
                        .userId(rt.getUserId())
                        .build()
                ).collect(Collectors.toList());
    }

    // 일을 가장 많이 한 멤버 선정 기능
    @Transactional(readOnly = true)
    public List<Long> findBestMember(Long roomId) {
        var room = roomStore.getRoom(roomId);
        var memberIdList = roomStore.findAllMemberId(room);

        var memberTodoCountList = todoStore.findAllMemberTodoCount(
                memberIdList,
                TimeUtil.startOfWeek(),
                TimeUtil.endOfWeek()
        );

        List<Long> bestMemberList = new ArrayList<>();
        long maxCount = 0;
        for (var memberTodoCount : memberTodoCountList) {
            maxCount = Math.max(maxCount, memberTodoCount.getTodoCount());
        }
        for (var memberTodoCount : memberTodoCountList) {
            if (memberTodoCount.getTodoCount() == maxCount) {
                bestMemberList.add(memberTodoCount.getUserId());
            }
        }
        return bestMemberList;
    }

    private List<RepeatDay> dayStrToRepeatDay(String days) {
        List<RepeatDay> repeatDayList = new ArrayList<>();
        for (TimeUtil.DAY day : TimeUtil.DAY.values()) {
            int dayNum = day.ordinal();
            if (days.charAt(dayNum) == '1') {
                var repeatDay = new RepeatDay(dayNum);
                repeatDayList.add(repeatDay);
            }
        }
        return repeatDayList;
    }

    private String repeatDayTodayStr(List<RepeatDay> repeatDayList) {
        StringBuilder sb = new StringBuilder("0000000");
        for (RepeatDay repeatDay : repeatDayList) {
            sb.setCharAt(repeatDay.getDay(), '1');
        }
        return sb.toString();
    }
}
