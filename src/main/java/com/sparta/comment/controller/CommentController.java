package com.sparta.comment.controller;

import com.sparta.comment.dto.CommentRequestDto;
import com.sparta.comment.dto.CommentResponseDto;
import com.sparta.comment.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{scheduleId}")
    public CommentResponseDto createComment(@PathVariable Long scheduleId, @RequestBody CommentRequestDto requestDto) {
        return commentService.createComment(scheduleId, requestDto);
    }

    @GetMapping
    public List<CommentResponseDto> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("/{id}")
    public CommentResponseDto getComment(@PathVariable Long id) {
        return commentService.getComment(id);
    }

    @PutMapping("/{id}")
    public Long updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto) {
        return commentService.updateComment(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public Long deleteComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto) {
        return commentService.deleteComment(id);
    }
}
