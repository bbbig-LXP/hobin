package com.lxp.domain;

import java.time.LocalDateTime;

public class Course {

    private Long id;
    private String title;
    private String description;
    private Long instructorId;
    private CourseStatus status;
    private CourseLevel courseLevel;
    private LocalDateTime publishedAt;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public Course(Long id, String title, String description, Long instructorId, CourseStatus status,
        CourseLevel courseLevel, LocalDateTime publishedAt, LocalDateTime createdAt,
        LocalDateTime updatedAt) {
        titleCheck(title);
        descriptionCheck(description);
        this.id = id;
        this.title = title;
        this.description = description;
        this.instructorId = instructorId;
        this.status = status;
        this.courseLevel = courseLevel;
        this.publishedAt = publishedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Course(String title, String description, Long instructorId, CourseLevel courseLevel) {
        titleCheck(title);
        descriptionCheck(description);
        if (instructorId == null || instructorId <= 0) {
            throw new IllegalArgumentException("강사 ID는 필수입니다");
        }
        if (courseLevel == null) {
            throw new IllegalArgumentException("코스 레벨은 필수입니다");
        }
        this.title = title;
        this.description = description;
        this.instructorId = instructorId;
        this.courseLevel = courseLevel;

        this.status = CourseStatus.DRAFT;
        this.publishedAt = null;
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

    private void descriptionCheck(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("내용은 비울 수 없습니다");
        }
        if (description.length() > 200) {
            throw new IllegalArgumentException("내용은 200자 이하여야 합니다");
        }
    }


    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Long getInstructorId() {
        return instructorId;
    }

    public CourseStatus getStatus() {
        return status;
    }

    public CourseLevel getCourseLevel() {
        return courseLevel;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public enum CourseStatus {
        DRAFT,
        PUBLISHED,
        ARCHIVED
    }


    public enum CourseLevel {
        BEGINNER,
        INTERMEDIATE,
        ADVANCED
    }

    @Override
    public String toString() {
        return "Course{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", instructorId=" + instructorId +
            ", status=" + status +
            ", courseLevel=" + courseLevel +
            ", publishedAt=" + publishedAt +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            '}';
    }

    public void publish() {
        if (this.status != CourseStatus.DRAFT) {
            throw new IllegalArgumentException("DRAFT 상태만 가능");
        }
        this.status = CourseStatus.PUBLISHED;
        this.publishedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void archived() {
        if (this.status != CourseStatus.PUBLISHED) {
            throw new IllegalArgumentException("PUBLISHED 상태만 보관 가능");
        }
        this.status = CourseStatus.ARCHIVED;
        this.publishedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void updateShield(String title, String description, CourseLevel level) {
        if (this.status == CourseStatus.PUBLISHED) {
            throw new IllegalArgumentException("발행된 강좌는 수정 불가");
        }
        titleCheck(title);
        descriptionCheck(description);
        if (level == null) {
            throw new IllegalArgumentException("코스 레벨 필수");
        }
        this.title = title;
        this.description = description;
        this.courseLevel = level;
        this.updatedAt = LocalDateTime.now();

    }


}

