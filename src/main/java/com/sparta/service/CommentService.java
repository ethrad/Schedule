package com.sparta.service;

import com.sparta.dto.request.CommentRequestDto;
import com.sparta.dto.response.CommentResponseDto;
import com.sparta.entity.Comment;
import com.sparta.repository.CommentRepository;
import com.sparta.common.ApplicationException;
import com.sparta.common.ErrorCode;
import com.sparta.entity.Schedule;
import com.sparta.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    public CommentService(CommentRepository commentRepository, ScheduleRepository scheduleRepository) {
        this.commentRepository = commentRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public CommentResponseDto createComment(Long scheduleId, CommentRequestDto requestDto) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ApplicationException(ErrorCode.SCHEDULE_NOT_FOUND)
        );

        Comment comment = new Comment(requestDto);
        schedule.addComment(comment);

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
    public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto) {
        Comment comment = findComment(id);
        comment.update(requestDto);
        return new CommentResponseDto(comment);
    }

    public void deleteComment(Long id) {
        Comment comment = findComment(id);
        commentRepository.delete(comment);
    }

    private Comment findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(
                () -> new ApplicationException(ErrorCode.COMMENT_NOT_FOUND)
        );
    }
}
