package com.sparta.schedule.repository;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Repository
public class ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Schedule save(Schedule schedule) {
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환받기 위한 객체

        String sql = "INSERT INTO schedule (username, description, password, createAt, updateAt) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, schedule.getUsername());
                    preparedStatement.setString(2, schedule.getDescription());
                    preparedStatement.setString(3, schedule.getPassword());
                    preparedStatement.setDate(4, Date.valueOf(LocalDate.now())); // createAt
                    preparedStatement.setDate(5, Date.valueOf(LocalDate.now())); // updateAt
                    return preparedStatement;
                },
                keyHolder);

        // DB Insert 후 받아온 기본키 확인
        Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        schedule.setId(id);

        return schedule;
    }

    public List<ScheduleResponseDto> findAll() {
        // 모든 일정 조회
        String sql = "SELECT * FROM schedule";

        return jdbcTemplate.query(sql, new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("id");
                String username = rs.getString("username");
                String description = rs.getString("description");
                LocalDate createAt = rs.getDate("createAt").toLocalDate();
                LocalDate updateAt = rs.getDate("updateAt").toLocalDate();
                return new ScheduleResponseDto(id, username, description, createAt, updateAt);
            }
        });
    }

    public void update(Long id, ScheduleRequestDto requestDto) {
        String sql = "UPDATE schedule SET username = ?, description = ?, updateAt =? WHERE id = ?";
        jdbcTemplate.update(sql, requestDto.getUsername(), requestDto.getDescription(), Date.valueOf(LocalDate.now()), id);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM schedule WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<ScheduleResponseDto> findByConditions(String username, LocalDate date) {
        // 기본 SQL 쿼리
        StringBuilder sql = new StringBuilder("SELECT * FROM schedule WHERE 1=1");

        // 조건이 있을 때만 쿼리에 추가
        if (username != null && !username.isEmpty()) {
            sql.append(" AND username = ?");
        }
        if (date != null) {
            sql.append(" AND updateAt = ?");
        }

        return jdbcTemplate.query(con -> {
            PreparedStatement ps = con.prepareStatement(sql.toString());
            int paramIndex = 1;

            if (username != null && !username.isEmpty()) {
                ps.setString(paramIndex++, username);
            }
            if (date != null) {
                ps.setDate(paramIndex++, java.sql.Date.valueOf(date));
            }

            return ps;
        }, new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("id");
                String username = rs.getString("username");
                String description = rs.getString("description");
                LocalDate createAt = rs.getDate("createAt").toLocalDate();
                LocalDate updateAt = rs.getDate("updateAt").toLocalDate();
                return new ScheduleResponseDto(id, username, description, createAt, updateAt);
            }
        });
    }

    public Schedule findById(Long id) {
        String sql = "SELECT * FROM schedule WHERE id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                Schedule schedule = new Schedule();
                schedule.setId(resultSet.getLong("id"));
                schedule.setUsername(resultSet.getString("username"));
                schedule.setDescription(resultSet.getString("description"));
                schedule.setPassword(resultSet.getString("password"));
                schedule.setCreateAt(resultSet.getDate("createAt").toLocalDate());
                schedule.setUpdateAt(resultSet.getDate("updateAt").toLocalDate());
                return schedule;
            } else {
                return null;
            }
        }, id);
    }
}
