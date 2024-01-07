package com.project.dungi.domain.todo.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RepeatDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="repeat_day_id")
    private Long id;

    private int day;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="todo_id")
    private RepeatTodo repeatTodo;

    public RepeatDay(int day){
        this.day = day;
    }

    public void setRepeatTodo(RepeatTodo repeatTodo){
        this.repeatTodo = repeatTodo;
        repeatTodo.getRepeatDayList().add(this);
    }
}
