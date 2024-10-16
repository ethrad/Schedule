package com.sparta.comments.repository;

import com.sparta.comments.dto.CommentResponseDto;
import com.sparta.comments.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByOrderByUpdatedAtDesc();
}
