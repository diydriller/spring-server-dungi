package com.dungi.core.domain.user.model;

import com.dungi.common.exception.BaseException;
import com.dungi.core.domain.common.BaseEntity;
import com.dungi.core.domain.common.DeleteStatus;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.io.Serializable;

import static com.dungi.common.response.BaseResponseStatus.INVALID_VALUE;


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

    private String provider;

    @Enumerated(EnumType.STRING)
    @Column(name = "delete_status")
    private DeleteStatus deleteStatus;

    @Column(name = "best_mate_count")
    private int bestMateCount;

    @Builder
    public User(
            String name,
            String nickname,
            String email,
            String password,
            String phoneNumber,
            String profileImg,
            String provider
    ) {
        if(StringUtils.isEmpty(nickname)) throw new BaseException(INVALID_VALUE);
        if(StringUtils.isEmpty(email)) throw new BaseException(INVALID_VALUE);
        if(StringUtils.isEmpty(password)) throw new BaseException(INVALID_VALUE);
        if(StringUtils.isEmpty(provider)) throw new BaseException(INVALID_VALUE);
        if(StringUtils.isEmpty(phoneNumber)) throw new BaseException(INVALID_VALUE);
        if(StringUtils.isEmpty(name)) throw new BaseException(INVALID_VALUE);

        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.profileImg = profileImg;
        this.provider = provider;
        this.deleteStatus = DeleteStatus.NOT_DELETED;
        this.bestMateCount = 0;
    }

    public void increaseBestMateCount() {
        this.bestMateCount++;
    }
}
