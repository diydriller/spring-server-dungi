package com.project.dungi.domain.vote.model;

import com.project.dungi.domain.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VoteItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="vote_item_id")
    private Long id;

    private String choice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="notice_vote_id")
    private Vote vote;

    @OneToMany(mappedBy = "voteItem")
    private List<UserVoteItem> userVoteItemList = new ArrayList<>();

    public VoteItem(String choice){
        this.choice = choice;
    }

    public void setVote(Vote vote){
        this.vote = vote;
    }
}
