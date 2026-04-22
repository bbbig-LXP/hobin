package com.lxp.Service;

import com.lxp.repository.CourseRepository;


public class CourseService {

    private final CourseRepository courseRepository;


    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }


}
