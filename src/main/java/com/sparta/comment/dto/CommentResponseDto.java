package com.sparta.comment.dto;

import com.sparta.comment.entity.Comment;

import java.time.LocalDateTime;

public class CommentResponseDto {
    private Long id;
    private String content;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment savedComment) {
        this.id = savedComment.getId();
        this.content = savedComment.getContent();
        this.username = savedComment.getUsername();
        this.createdAt = savedComment.getCreatedAt();
        this.modifiedAt = savedComment.getModifiedAt();
    }
}
