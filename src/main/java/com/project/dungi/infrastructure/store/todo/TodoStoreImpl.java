package com.project.dungi.infrastructure.store.todo;

import com.project.dungi.domain.common.DeleteStatus;
import com.project.dungi.domain.common.FinishStatus;

import com.project.dungi.domain.todo.model.RepeatDay;
import com.project.dungi.domain.todo.model.RepeatTodo;
import com.project.dungi.domain.todo.model.TodayTodo;
import com.project.dungi.domain.todo.service.TodoStore;
import com.project.dungi.infrastructure.jpa.todo.RepeatDayJdbcRepository;
import com.project.dungi.infrastructure.jpa.todo.TodoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TodoStoreImpl implements TodoStore {

    private final TodoJpaRepository todoJpaRepository;
    private final RepeatDayJdbcRepository repeatDayJdbcRepository;

    @Override
    public void saveTodayTodo(TodayTodo todayTodo) {
        todoJpaRepository.save(todayTodo);
    }

    @Override
    public void saveRepeatTodo(RepeatTodo repeatTodo, List<RepeatDay> repeatDayList) {
        var savedRepeatTodo = todoJpaRepository.save(repeatTodo);
        repeatDayList.forEach(rd -> rd.setRepeatTodo(savedRepeatTodo));
        repeatDayJdbcRepository.saveAll(repeatDayList);
    }

    @Override
    public List<TodayTodo> findTodayTodo(Long roomId, Long userId, int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "createdTime");
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime todayLastTime = LocalDate.now().plusDays(1).atStartOfDay();
        return todoJpaRepository.findAllPossibleTodayTodo(
                roomId,
                DeleteStatus.NOT_DELETED,
                FinishStatus.UNFINISHED,
                currentTime,
                todayLastTime,
                pageRequest
        );
    }

    @Override
    public List<RepeatTodo> findRepeatTodo(Long roomId, Long userId, int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "createdTime");
        return todoJpaRepository.findAllPossibleRepeatTodo(
                roomId,
                DeleteStatus.NOT_DELETED,
                pageRequest
        );
    }
}
