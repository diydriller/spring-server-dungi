package com.project.dungi.domain.todo.model;

import com.project.dungi.domain.common.DeleteStatus;
import com.project.dungi.domain.room.model.Room;
import com.project.dungi.domain.user.model.User;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = "R")
public class RepeatTodo extends Todo {

    @OneToMany(mappedBy = "repeatTodo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RepeatDay> repeatDayList = new ArrayList<>();

    @Builder
    public RepeatTodo (
            Long roomId,
            Long userId,
            LocalDateTime deadline,
            String todoItem,
            List<RepeatDay> repeatDays
    ){
        super.setRoomId(roomId);
        super.setUserId(userId);
        super.setDeadline(deadline);
        super.setTodoItem(todoItem);
        super.setDeleteStatus(DeleteStatus.NOT_DELETED);
        for(RepeatDay repeatDay:repeatDays){
            addRepeatDay(repeatDay);
        }
    }

    private void addRepeatDay(RepeatDay repeatDay){
        repeatDayList.add(repeatDay);
        repeatDay.setRepeatTodo(this);
    }
}
