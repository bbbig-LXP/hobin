package com.lxp.Service;

import com.lxp.domain.Course;
import com.lxp.repository.CourseRepository;


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

}
