package com.lxp.domain;

import java.time.LocalDateTime;

public class Content {

    private Long id;
    private Long sectionId;
    private String title;
    private ContentType contentType;
    private ContentStatus contentStatus;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public Content(Long sectionId, String title, ContentType contentType) {
        this(null, sectionId, title, contentType, ContentStatus.NORMAL, LocalDateTime.now(),
            LocalDateTime.now());
    }

    public Content(Long id, Long sectionId, String title, ContentType contentType,
        ContentStatus contentStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        if (sectionId == null || sectionId <= 0) {
            throw new IllegalArgumentException("섹션 ID 필수입니다");
        }
        titleCheck(title);
        this.id = id;
        this.sectionId = sectionId;
        this.title = title;
        this.contentType = contentType;
        this.contentStatus = contentStatus;
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

    public Long getSectionId() {
        return sectionId;
    }

    public String getTitle() {
        return title;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public ContentStatus getContentStatus() {
        return contentStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public enum ContentStatus {
        NORMAL,
        HIDDEN
    }

    public enum ContentType {
        VIDEO,
        DOCUMENT
    }
}
