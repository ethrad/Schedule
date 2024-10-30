package com.sparta.schedule.repository;

import com.sparta.schedule.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Page<Schedule> findAll(Pageable pageable);
    @Query("SELECT s FROM Schedule s JOIN s.users u WHERE u.username = :username AND s.modifiedAt = :modifiedAt")
    List<Schedule> findByUsernameAndModifiedAt(@Param("username") String username, @Param("modifiedAt") LocalDateTime modifiedAt);
}
