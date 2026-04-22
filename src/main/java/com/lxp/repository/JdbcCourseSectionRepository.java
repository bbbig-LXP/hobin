package com.lxp.repository;

import com.lxp.common.DBConnectionManager;
import com.lxp.domain.CourseSection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class JdbcCourseSectionRepository implements CourseSectionRepository {


    @Override
    public CourseSection save(CourseSection courseSection) {
        String sql = "INSERT INTO course_sections (course_id, title, status) VALUES (?, ?, ?)";

        try (Connection conn = DBConnectionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setLong(1, courseSection.getCourseId());
            pstmt.setString(2, courseSection.getTitle());
            pstmt.setString(3, courseSection.getStatus().name());

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    Long id = rs.getLong(1);
                    return new CourseSection(
                        id,
                        courseSection.getCourseId(),
                        courseSection.getTitle(),
                        courseSection.getStatus(),
                        LocalDateTime.now(),
                        LocalDateTime.now()
                    );

                } else {
                    throw new SQLException("생성되었으나 ID를 가져오지 못함");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("강의 섹션 저장 에러", e);
        }
    }

    @Override
    public Optional<CourseSection> findById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("조회할 ID는 1 이상이여야 합니다");
        }
        String sql = "SELECT * FROM course_sections WHERE id = ? AND status != 'ARCHIVED' ";

        try (Connection conn = DBConnectionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    CourseSection section = new CourseSection(
                        rs.getLong("id"),
                        rs.getLong("course_id"),
                        rs.getString("title"),
                        CourseSection.CourseSectionStatus.valueOf(rs.getString("status")),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                    );
                    return Optional.of(section);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("강의 섹션 단일 조회 에러", e);
        }
        return Optional.empty();


    }

    @Override
    public List<CourseSection> findAll(Long courseId) {
        if (courseId == null || courseId <= 0) {
            throw new IllegalArgumentException("조회할 ID는 1 이상이여야 합니다");
        }
        String sql = "SELECT * FROM course_sections WHERE course_id = ? AND status != 'ARCHIVED' ";

        List<CourseSection> sections = new ArrayList<>();

        try (Connection conn = DBConnectionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, courseId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    CourseSection section = new CourseSection(
                        rs.getLong("id"),
                        rs.getLong("course_id"),
                        rs.getString("title"),
                        CourseSection.CourseSectionStatus.valueOf(rs.getString("status")),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                    );
                    sections.add(section);
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException("강의 섹션 전체 조회 에러", e);


        }
        return sections;


    }

    @Override
    public void update(CourseSection courseSection) {
        if (courseSection == null || courseSection.getId() == null) {
            throw new IllegalArgumentException("수정 실패 아직 저장되지 않은 강좌 입니다(ID null");
        }
        String sql = "UPDATE course_sections SET title = ?, status = ? , updated_at = NOW() WHERE id = ?";

        try (Connection conn = DBConnectionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, courseSection.getTitle());
            pstmt.setString(2, courseSection.getStatus().name());
            pstmt.setLong(3, courseSection.getId());

            int rows = pstmt.executeUpdate();
            if (rows == 0) {
                throw new SQLException("섹션 수정 실패 : ID 못찾음");
            }
            System.out.println("섹션 수정 완료");
        } catch (SQLException e) {
            throw new RuntimeException("섹션 수정 에러", e);
        }

    }

    @Override
    public boolean softDelete(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("삭제할 ID는 1 이상이여야 합니다");
        }
        String sql = "UPDATE course_sections SET status = 'ARCHIVED' WHERE id = ? AND status != 'ARCHIVED'";

        try (Connection conn = DBConnectionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);

            int deleteCount = pstmt.executeUpdate();

            if (deleteCount == 0) {
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException("섹션 삭제 실패", e);
        }
        return true;


    }

}
