package com.project.dungi.domain.vote.model;

import com.project.dungi.common.exception.BaseException;
import com.project.dungi.domain.common.BaseEntity;
import com.project.dungi.domain.common.DeleteStatus;
import com.project.dungi.domain.common.FinishStatus;
import com.project.dungi.domain.room.model.Room;
import com.project.dungi.domain.user.model.User;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.project.dungi.common.response.BaseResponseStatus.INVALID_VALUE;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vote extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_id")
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name="vote_status")
    private FinishStatus finishStatus;

    @OneToMany(mappedBy = "vote",cascade = CascadeType.ALL)
    private List<VoteItem> voteItemList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "delete_status")
    private DeleteStatus deleteStatus;

    @Column(name="user_id")
    private Long userId;

    @Column(name="room_id")
    private Long roomId;

    @Builder
    public Vote(
            Long roomId,
            Long userId,
            String title,
            List<VoteItem> voteItems
    ){
        if(userId == null) throw new BaseException(INVALID_VALUE);
        if(roomId == null) throw new BaseException(INVALID_VALUE);
        if(voteItems == null || voteItems.size()==0) throw new BaseException(INVALID_VALUE);
        if(StringUtils.isEmpty(title)) throw new BaseException(INVALID_VALUE);

        this.roomId = roomId;
        this.userId = userId;
        this.deleteStatus = DeleteStatus.NOT_DELETED;
        this.finishStatus = FinishStatus.UNFINISHED;
        this.title = title;
        for(VoteItem voteItem : voteItems){
            addVoteItem(voteItem);
        }
    }

    private void addVoteItem(VoteItem voteItem){
        voteItemList.add(voteItem);
        voteItem.setVote(this);
    }
}
