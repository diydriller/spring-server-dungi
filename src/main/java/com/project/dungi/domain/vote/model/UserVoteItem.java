package com.project.dungi.domain.vote.model;

import com.project.dungi.domain.common.BaseEntity;
import com.project.dungi.domain.common.DeleteStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserVoteItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="users_vote_item_id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="vote_item_id")
    private VoteItem voteItem;

    @Column(name="users_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name="delete_status")
    private DeleteStatus deleteStatus;

    public UserVoteItem(Long userId, VoteItem voteItem){
        this.userId = userId;
        setVoteItem(voteItem);
        this.deleteStatus = DeleteStatus.NOT_DELETED;
    }

    private void setVoteItem(VoteItem voteItem){
        this.voteItem = voteItem;
        voteItem.getUserVoteItemList().add(this);
    }

    public void changeChoice(){
        if(this.deleteStatus == DeleteStatus.DELETED){
            this.deleteStatus = DeleteStatus.NOT_DELETED;
            return;
        }
        this.deleteStatus = DeleteStatus.DELETED;
    }
}
