package com.lxp.repository;

import com.lxp.common.DBConnectionManager;
import com.lxp.domain.Course;

import java.sql.*;
import java.util.Optional;

public class JdbcCourseRepository implements CourseRepository{


    @Override
    public Course save(Course course){
        String sql = "INSERT INTO Courses (title, description, status, level) VALUES(?, ?, ?, ?)";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            pstmt.setString(1, course.getTitle());
            pstmt.setString(2, course.getDescription());
            pstmt.setString(3, course.getStatus().name());
            pstmt.setString(4, course.getCourseLevel().name());

            pstmt.executeUpdate();


            try(ResultSet rs = pstmt.getGeneratedKeys()){
                if(rs.next()){
                    Long id = rs.getLong(1);
                    course.setId(id);
                    System.out.println("로그 : DB 저장 성공" + id);

                }

            }


        }catch (SQLException e){
            System.err.println("로그 저장 실패");
            throw new RuntimeException(e);
        }

        return course;


    }

    @Override
    public Optional<Course> findById(Long id){
        String sql = "SELECT * FROM Courses WHERE id = ?";

        try(Connection conn = DBConnectionManager.getConnection();
            PreparedStatement pstmt =  conn.prepareStatement(sql)){

            pstmt.setLong(1,id);

            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    Course course = new Course(
                            rs.getString("title"),
                            rs.getString("description"),
                            Course.CourseLevel.valueOf(rs.getString("level"))

                    );
                    course.setId(rs.getLong("id"));
                    course.setStatus(Course.CourseStatus.valueOf(rs.getString("status")));

                    return Optional.of(course);
                }

            }
        }catch (SQLException e){
            throw new RuntimeException("조회 중 에러" + e);
        }return Optional.empty();


    }

    @Override
    public void update(Course course) {
        String sql = "UPDATE Courses SET title =? , description = ? , status = ? ,level = ?, updated_at = NOW() WHERE id = ?";


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
        }catch (SQLException e){
            throw new RuntimeException("수정중 에러 발생" + e);
        }


    }
}
