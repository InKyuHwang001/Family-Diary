package com.family.hwang.model.entity;

import com.family.hwang.controller.request.UserSignUpRequest;
import com.family.hwang.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.sql.Timestamp;

import static java.time.Instant.now;

@Entity
@Getter
@Table(name = "\"user\"") //postgreSQL의 경우 user table이 따로 존재하기에
@SQLDelete(sql = "UPDATE \"user\" SET removed_at = NOW() WHERE id=?")
@Where(clause = "removed_at is NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    private String password;

    private String email;

    @Column(name = "registered_at")
    private Timestamp registeredAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "removed_at")
    private Timestamp removedAt;

    @PrePersist
    void registeredAt(){
        this.registeredAt = Timestamp.from(now());
        this.updatedAt = Timestamp.from(now());
    }

    @PreUpdate
    void updatedAt(){
        this.updatedAt = Timestamp.from(now());
    }


    @Builder
    private UserEntity(Long id, String userName, String password, String email) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public static UserEntity of(String userName, String password, String email){

        return UserEntity.builder()
                .userName(userName)
                .email(email)
                .password(password)
                .build();
    }

}
