package com.sparta.comments.dto;

import com.sparta.comments.entity.Comment;

import java.time.LocalDateTime;

public class CommentResponseDto {
    private Long id;
    private String content;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CommentResponseDto(Comment savedComment) {
        this.id = savedComment.getId();
        this.content = savedComment.getContent();
        this.username = savedComment.getUsername();
        this.createdAt = savedComment.getCreatedAt();
        this.updatedAt = savedComment.getUpdatedAt();
    }
}
