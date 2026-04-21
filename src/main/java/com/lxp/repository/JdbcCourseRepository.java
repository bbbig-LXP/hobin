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
    public Optional<Course> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void update(Course course) {

    }
}
