package com.project.dungi.domain.todo.service;

import com.project.dungi.common.util.TimeUtil;
import com.project.dungi.domain.room.service.RoomStore;
import com.project.dungi.domain.todo.dto.GetRepeatTodoDto;
import com.project.dungi.domain.todo.model.RepeatDay;
import com.project.dungi.domain.todo.model.RepeatTodo;
import com.project.dungi.domain.todo.model.TodayTodo;
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

    private List<RepeatDay> dayStrToRepeatDay(String days){
        List<RepeatDay> repeatDayList = new ArrayList<>();
        for(TimeUtil.DAY day : TimeUtil.DAY.values()) {
            int dayNum = day.ordinal();
            if (days.charAt(dayNum) == '1') {
                var repeatDay = new RepeatDay(dayNum);
                repeatDayList.add(repeatDay);
            }
        }
        return repeatDayList;
    }

    private String repeatDayTodayStr(List<RepeatDay> repeatDayList){
        StringBuilder sb = new StringBuilder("0000000");
        for(RepeatDay repeatDay : repeatDayList){
            sb.setCharAt(repeatDay.getDay(),'1');
        }
        return sb.toString();
    }
}
