package com.sparta.comments.service;

import com.sparta.comments.dto.CommentRequestDto;
import com.sparta.comments.dto.CommentResponseDto;
import com.sparta.comments.entity.Comment;
import com.sparta.comments.repository.CommentRepository;
import com.sparta.schedule.entity.Schedule;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public CommentResponseDto createComment(CommentRequestDto requestDto) {
        Comment comment = new Comment(requestDto);

        Comment savedComment = commentRepository.save(comment);

        return new CommentResponseDto(savedComment);
    }

    public CommentResponseDto getComment(Long id) {
        return new CommentResponseDto(findComment(id));
    }

    public List<CommentResponseDto> getAllComments() {
        return commentRepository.findAll().stream().map(CommentResponseDto::new).toList();
    }

    @Transactional
    public Long updateComment(Long id, CommentRequestDto requestDto) {
        Comment comment = findComment(id);
        comment.update(requestDto);
        return id;
    }

    public Long deleteComment(Long id) {
        Comment comment = findComment(id);
        commentRepository.delete(comment);
        return id;
    }

    private Comment findComment(Long id){
        return commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Comment not found"));
    }
}
