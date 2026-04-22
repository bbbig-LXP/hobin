package com.lxp.domain;

import java.time.LocalDateTime;

public class CourseSection {

    private Long id;
    private Long courseId;
    private String title;
    private CourseSectionStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public CourseSection(Long courseId, String title) {
        this(null, courseId, title, CourseSectionStatus.DRAFT, LocalDateTime.now(),
            LocalDateTime.now());
    }

    public CourseSection(Long id, Long courseId, String title, CourseSectionStatus status,
        LocalDateTime createdAt, LocalDateTime updatedAt) {
        if (courseId == null || courseId <= 0) {
            throw new IllegalArgumentException("강좌 ID는 필수입니다");
        }
        this.id = id;
        this.courseId = courseId;
        this.title = title;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public CourseSectionStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public enum CourseSectionStatus {
        DRAFT,
        PUBLISHED,
        ARCHIVED,
    }
}
