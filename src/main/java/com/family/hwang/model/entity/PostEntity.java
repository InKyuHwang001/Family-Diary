package com.family.hwang.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity
@Table(name = "\"post\"")
@SQLDelete(sql = "UPDATE \"post\" SET removed_at = NOW() WHERE id=?")
@Where(clause = "removed_at is NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "body", columnDefinition = "TEXT")
    private String body;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Builder
    public PostEntity(Long id, String title, String body, UserEntity user) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.user = user;
    }

    public void edit(String title, String body) {
        if (title != null) {
            this.title = title;
        }
        if (body != null) {
            this.body = body;
        }
    }
}