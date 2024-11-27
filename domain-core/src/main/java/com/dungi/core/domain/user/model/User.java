package com.dungi.core.domain.user.model;

import com.dungi.core.domain.common.model.BaseEntity;
import com.dungi.core.domain.common.value.DeleteStatus;
import com.dungi.common.value.Provider;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@ToString
@Getter
@Entity
@Table(name = "users", indexes = @Index(name = "user_idx", columnList = "email", unique = true))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Long id;

    private String name;

    private String nickname;

    private String email;

    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "profile_img")
    private String profileImg;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Enumerated(EnumType.STRING)
    @Column(name = "delete_status")
    private DeleteStatus deleteStatus;

    @Builder
    public User(
            String name,
            String nickname,
            String email,
            String password,
            String phoneNumber,
            String profileImg,
            Provider provider
    ) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.profileImg = profileImg;
        this.provider = provider;
        this.deleteStatus = DeleteStatus.NOT_DELETED;
    }
}
