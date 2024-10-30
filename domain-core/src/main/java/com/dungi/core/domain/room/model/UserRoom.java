package com.dungi.core.domain.room.model;

import com.dungi.common.exception.BaseException;
import com.dungi.core.domain.common.BaseEntity;
import com.dungi.core.domain.common.DeleteStatus;
import lombok.*;

import javax.persistence.*;

import static com.dungi.common.response.BaseResponseStatus.INVALID_VALUE;


@Getter
@Entity
@Table(name = "user_room")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="users_room_id")
    private Long id;

    @Column(name = "users_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Enumerated(EnumType.STRING)
    @Column(name = "delete_status")
    private DeleteStatus deleteStatus;

    public UserRoom(Long userId, Room room){
        if(userId == null) throw new BaseException(INVALID_VALUE);
        if(room == null) throw new BaseException(INVALID_VALUE);

        this.userId = userId;
        setRoom(room);
        this.deleteStatus = DeleteStatus.NOT_DELETED;
    }

    public void leave(){
        this.deleteStatus = DeleteStatus.DELETED;
    }

    public void enter(){
        this.deleteStatus = DeleteStatus.NOT_DELETED;
    }

    private void setRoom(Room room){
        this.room = room;
        room.getUserRoomList().add(this);
    }
}
