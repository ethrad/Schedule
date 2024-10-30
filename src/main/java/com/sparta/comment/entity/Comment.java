package com.sparta.comment.entity;

import com.sparta.comment.dto.CommentRequestDto;
import com.sparta.entity.Timestamped;
import com.sparta.schedule.entity.Schedule;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

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
