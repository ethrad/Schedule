package com.sparta.entity;

import com.sparta.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "user")
@NoArgsConstructor
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", nullable = false, length = 30)
    private String username;
    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<Schedule> schedules = new ArrayList<>();

    public User(UserRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.email = requestDto.getEmail();
    }

    public void update(UserRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.email = requestDto.getEmail();
    }

    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
        schedule.addUser(this);
    }
}
