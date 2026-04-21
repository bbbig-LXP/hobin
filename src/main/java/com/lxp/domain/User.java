package com.lxp.domain;

import java.time.LocalDateTime;

public class User {

    private Long id;
    private String name;
    private UsersType type;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User(String name, UsersType type) {

        nameCheck(name);

        this.id = null;
        this.name = name;
        this.type = type;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }


    private void nameCheck(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 공백일 수 없습니다");
        }
        if (name.length() > 50 || name.length() < 2) {
            throw new IllegalArgumentException("이름은 최소 2자 이상 50자 이하여야 합니다");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public UsersType getType() {
        return type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public enum UsersType {
        STUDENT,
        INSTRUCTOR,
        ADMIN
    }
}
