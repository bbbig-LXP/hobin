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

    public void setStatus(CourseStatus status) {
        this.status = status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public Course(String title, String description, CourseLevel courseLevel) {
        this(title,description,1L,courseLevel);
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


}
