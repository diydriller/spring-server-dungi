package com.dungi.core.domain.room.model;

import com.dungi.common.exception.BaseException;
import com.dungi.core.domain.common.BaseEntity;
import com.dungi.core.domain.common.DeleteStatus;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.dungi.common.response.BaseResponseStatus.INVALID_VALUE;


@Getter
@Entity
@Table(name = "room")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    private String name;

    private String color;

    @Enumerated(EnumType.STRING)
    @Column(name = "delete_status")
    private DeleteStatus deleteStatus;

    @OneToMany(mappedBy = "room")
    private List<UserRoom> userRoomList = new ArrayList<>();

    public Room(String name, String color){
        if(StringUtils.isEmpty(name)) throw new BaseException(INVALID_VALUE);
        if(StringUtils.isEmpty(color)) throw new BaseException(INVALID_VALUE);

        this.color = color;
        this.name = name;
        this.deleteStatus = DeleteStatus.NOT_DELETED;
    }

    public void deactivate(){
        this.deleteStatus = DeleteStatus.DELETED;
    }
}
