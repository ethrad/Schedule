package com.sparta.schedule.repository;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

        String sql = "INSERT INTO memo (username, description, password) VALUES (?, ?, ?)";
        jdbcTemplate.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, schedule.getUsername());
                    preparedStatement.setString(2, schedule.getDescription());
                    preparedStatement.setInt(3, schedule.getPassword());
                    return preparedStatement;
                },
                keyHolder);

        // DB Insert 후 받아온 기본키 확인
        Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        schedule.setId(id);

        return schedule;
    }

    public List<ScheduleResponseDto> findAll() {
        // DB 조회
        String sql = "SELECT * FROM schedule";

        return jdbcTemplate.query(sql, new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("id");
                String username = rs.getString("username");
                String description = rs.getString("description");
                return new ScheduleResponseDto(id, username, description);
            }
        });
    }

    public void update(Long id, ScheduleRequestDto requestDto) {
        String sql = "UPDATE schedule SET username = ?, description = ? WHERE id = ?";
        jdbcTemplate.update(sql, requestDto.getUsername(), requestDto.getDescription(), id);
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
            sql.append(" AND createAt = ?");
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
                schedule.setUsername(resultSet.getString("username"));
                schedule.setDescription(resultSet.getString("description"));
                return schedule;
            } else {
                return null;
            }
        }, id);
    }
}
