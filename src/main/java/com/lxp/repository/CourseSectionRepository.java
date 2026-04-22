package com.lxp.repository;

import com.lxp.domain.CourseSection;
import java.util.List;
import java.util.Optional;

public interface CourseSectionRepository {

    CourseSection save(CourseSection courseSection);

    Optional<CourseSection> findById(Long id);

    List<CourseSection> findAll(Long courseId);

    void update(CourseSection courseSection);

    boolean softDelete(Long id);

}