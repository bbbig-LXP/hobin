package com.lxp.domain;

import java.time.LocalDateTime;

public class CourseSection {

    private Long id;
    private Long courseId;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CourseSection(Long courseId, String title) {
        if (courseId == null) {
            throw new IllegalArgumentException("강좌 ID는 필수입니다");
        }
        titleCheck(title);
        this.id = null;
        this.courseId = courseId;
        this.title = title;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    private void titleCheck(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("타이틀은 비울 수 없습니다");
        }
        if (title.length() < 2 || title.length() > 50) {
            throw new IllegalArgumentException("타이틀은 최소 2자 이상 50자 이하여야 합니다");
        }
    }

    public Long getId() {
        return id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
