package com.family.hwang.model;

import com.family.hwang.model.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Getter
public class User implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private String email;
    private UserRole role;


    private Timestamp registeredAt;
    private Timestamp updatedAt;
    private Timestamp removedAt;

    @Builder
    private User(Long id, String username, String password, String email, UserRole role, Timestamp registeredAt, Timestamp updatedAt, Timestamp removedAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.registeredAt = registeredAt;
        this.updatedAt = updatedAt;
        this.removedAt = removedAt;
    }


    public static User fromEntity(UserEntity entity){
        return User.builder()
                .id(entity.getId())
                .username(entity.getUserName())
                .password(entity.getPassword())
                .email(entity.getEmail())
                .role(entity.getRole())
                .registeredAt(entity.getRegisteredAt())
                .updatedAt(entity.getUpdatedAt())
                .removedAt(entity.getRemovedAt())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.getRole().toString()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.removedAt == null;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.removedAt == null;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.removedAt == null;
    }

    @Override
    public boolean isEnabled() {
        return this.removedAt == null;
    }
}
