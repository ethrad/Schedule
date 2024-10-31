package com.sparta.entity;

import com.sparta.dto.request.ScheduleRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "schedule")
@NoArgsConstructor
public class Schedule extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "userId", nullable = false)
    private Long userId;
    @Column(name = "title", nullable = false, length = 100)
    private String title;
    @Column(name = "contents", nullable = false, length = 500)
    private String description;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "schedule_user", // 중간 테이블 이름
    joinColumns = @JoinColumn(name = "schedule_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users = new ArrayList<>();

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setSchedule(this);
    }

    public void addUser(User user) {
        users.add(user);
        user.getSchedules().add(this);
    }

    public Schedule(ScheduleRequestDto dto) {
        this.userId = dto.getUserId();
        this.title = dto.getTitle();
        this.description = dto.getDescription();
    }

    public void update(ScheduleRequestDto requestDto) {
        this.userId = requestDto.getUserId();
        this.title = requestDto.getTitle();
        this.description = requestDto.getDescription();
    }
}
