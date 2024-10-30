package com.dungi.core.domain.memo.model;


import com.dungi.common.exception.BaseException;
import com.dungi.core.domain.common.BaseEntity;
import com.dungi.core.domain.common.DeleteStatus;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;

import static com.dungi.common.response.BaseResponseStatus.INVALID_VALUE;


@Getter
@Entity
@Table(name = "memo")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Memo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memo_id")
    private Long id;

    @Column(name = "memo_item")
    private String memoItem;

    @Column(name = "x_position")
    private Double xPosition;

    @Column(name = "y_position")
    private Double yPosition;

    @Column(name = "memo_color")
    private String memoColor;

    @Enumerated(EnumType.STRING)
    @Column(name = "delete_status")
    private DeleteStatus deleteStatus;

    @Column(name="users_id")
    private Long userId;

    @Column(name="room_id")
    private Long roomId;

    @Builder
    public Memo(
            Long userId,
            Long roomId,
            String memoItem,
            Double xPosition,
            Double yPosition,
            String memoColor
    ){
        if(xPosition == null) throw new BaseException(INVALID_VALUE);
        if(yPosition == null) throw new BaseException(INVALID_VALUE);
        if(userId == null) throw new BaseException(INVALID_VALUE);
        if(roomId == null) throw new BaseException(INVALID_VALUE);
        if(StringUtils.isEmpty(memoItem)) throw new BaseException(INVALID_VALUE);
        if(StringUtils.isEmpty(memoColor)) throw new BaseException(INVALID_VALUE);

        this.userId = userId;
        this.roomId = roomId;
        this.memoItem = memoItem;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.memoColor = memoColor;
        this.deleteStatus = DeleteStatus.NOT_DELETED;
    }

    public void changeItem(String memoItem){
        this.memoItem = memoItem;
    }

    public void changeColor(String memoColor){
        this.memoColor = memoColor;
    }

    public void deactivate(){
        this.deleteStatus = DeleteStatus.DELETED;
    }

    public void move(double xPosition, double yPosition){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }
}
