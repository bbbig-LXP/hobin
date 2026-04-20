package com.lxp.domain;

import java.time.LocalDateTime;

public class Enrollment {

    private final Long id;
    private final Long courseId;
    private final Long userId;
    private LocalDateTime completedAt;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Enrollment(Long courseId, Long userId) {
        if (courseId == null || courseId <= 0) {
            throw new IllegalArgumentException("강좌 ID 는 비우거나 중복될 수 없습니다");
        }
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("유저 ID 는 비우거나 중복될 수 없습니다");
        }
        this.id = null;
        this.courseId = courseId;
        this.userId = userId;
        this.completedAt = null;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    private void complete(LocalDateTime completedAt) {
        if (completedAt != null) {
            throw new IllegalArgumentException("이미 완료된 수강입니다");
        }
    }

    public Long getId() {
        return id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public Long getUserId() {
        return userId;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
