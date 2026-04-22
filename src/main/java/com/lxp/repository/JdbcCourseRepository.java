package com.lxp.repository;

import com.lxp.common.DBConnectionManager;
import com.lxp.domain.Course;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Optional;

public class JdbcCourseRepository implements CourseRepository {


    @Override
    public Course save(Course course) {
        String sql = "INSERT INTO courses (title, description, status, level , instructor_id) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = DBConnectionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, course.getTitle());
            pstmt.setString(2, course.getDescription());
            pstmt.setString(3, course.getStatus().name());
            pstmt.setString(4, course.getCourseLevel().name());
            pstmt.setLong(5, course.getInstructorId());

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    Long id = rs.getLong(1);
                    return new Course(
                        id,
                        course.getTitle(),
                        course.getDescription(),
                        course.getInstructorId(),
                        course.getStatus(),
                        course.getCourseLevel(),
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        LocalDateTime.now()

                    );
                } else {
                    throw new SQLException("저장 했으나 ID를 가져오지 못함");
                }
            }
        } catch (SQLException e) {
            System.err.println("로그 저장 실패");
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Course> findById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("조회할 ID는 1 이상이여야 합니다");
        }
        String sql = "SELECT * FROM courses WHERE id = ?";

        try (Connection conn = DBConnectionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Course(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getLong("course_id"),
                        Course.CourseStatus.valueOf(rs.getString("status")),
                        Course.CourseLevel.valueOf(rs.getString("level")),
                        rs.getTimestamp("published_at").toLocalDateTime(),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                    ));
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException("조회 중 에러" + e);
        }
        return Optional.empty();


    }

    @Override
    public void update(Course course) {
        if (course == null || course.getId() == null) {
            throw new IllegalArgumentException("수정 실패 아직 저장되지 않은 강좌 입니다(ID null");
        }
        String sql = "UPDATE courses SET title =? , description = ? , status = ? ,level = ?, updated_at = NOW() WHERE id = ?";

        try (Connection conn = DBConnectionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, course.getTitle());
            pstmt.setString(2, course.getDescription());
            pstmt.setString(3, course.getStatus().name());
            pstmt.setString(4, course.getCourseLevel().name());
            pstmt.setLong(5, course.getId());

            int rows = pstmt.executeUpdate();
            if (rows == 0) {
                throw new SQLException("수정 실패 : 해당 강좌 ID 못찾음");
            }
            System.out.println("로그 : DB 수정 완료");
        } catch (SQLException e) {
            throw new RuntimeException("수정중 에러 발생", e);
        }


    }

    @Override
    public boolean softDelete(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("삭제할 ID는 1 이상이여야 합니다");
        }
        String sql = "UPDATE courses SET status = 'ARCHIVED' WHERE id = ? AND status != 'ARCHIVED' ";

        try (Connection conn = DBConnectionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);

            int deleteCount = pstmt.executeUpdate();

            if (deleteCount == 0) {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException("삭제 실패", e);
        }
        return true;
    }
}
