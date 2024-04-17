package com.family.hwang.model.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.Instant;

@Getter
@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "registered_at")
    protected Timestamp registeredAt;

    @Column(name = "updated_at")
    protected Timestamp updatedAt;

    @Column(name = "removed_at")
    protected Timestamp removedAt;

    @PrePersist
    void registeredAt() {
        this.registeredAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }
}
