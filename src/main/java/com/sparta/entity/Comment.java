package com.sparta.entity;

import com.sparta.dto.request.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "comment")
@RequiredArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 500)
    private String content;
    @Column(nullable = false)
    private String username;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    public Comment(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();
        this.username = requestDto.getUsername();
    }

    public void update(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();
        this.username = requestDto.getUsername();
    }
}
