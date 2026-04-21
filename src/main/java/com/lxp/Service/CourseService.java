package com.lxp.Service;

import com.lxp.common.DBConnectionManager;
import com.lxp.domain.Course;
import com.lxp.repository.CourseRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CourseService {

    private final CourseRepository courseRepository;


    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }


    public Long registerCourse(String title , String description , Course.CourseLevel courseLevel){
        Course course = new Course(title, description, courseLevel);

            Course saveCourse = courseRepository.save(course);

            return saveCourse.getId();


    }
    public void publishCourse(Long courseId){

    }

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
