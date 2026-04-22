package com.lxp.repository;

import com.lxp.domain.Course;

import java.util.Optional;

public interface CourseRepository {

    Course save(Course course);

    Optional<Course> findById(Long id);

    void update(Course course);

    boolean softDelete(Long id);

}
