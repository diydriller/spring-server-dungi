package com.project.dungi.domain.todo.model;


import com.project.dungi.common.exception.BaseException;
import com.project.dungi.domain.common.DeleteStatus;
import com.project.dungi.domain.common.FinishStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.project.dungi.common.response.BaseResponseStatus.INVALID_VALUE;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = "T")
public class TodayTodo extends Todo{

    @Enumerated(EnumType.STRING)
    @Column(name="todo_status")
    private FinishStatus finishStatus;

    @Builder
    public TodayTodo (
            Long roomId,
            Long userId,
            LocalDateTime deadline,
            String todoItem
    ){
        if(StringUtils.isEmpty(todoItem)) throw new BaseException(INVALID_VALUE);
        if(deadline == null) throw new BaseException(INVALID_VALUE);
        if(roomId == null) throw new BaseException(INVALID_VALUE);
        if(userId == null) throw new BaseException(INVALID_VALUE);

        super.setRoomId(roomId);
        super.setUserId(userId);
        super.setDeadline(deadline);
        super.setDeleteStatus(DeleteStatus.NOT_DELETED);
        super.setTodoItem(todoItem);
        finishStatus = FinishStatus.UNFINISHED;
    }
}
