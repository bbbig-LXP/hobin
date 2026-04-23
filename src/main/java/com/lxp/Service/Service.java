package com.lxp.Service;

import com.lxp.domain.Content;
import com.lxp.domain.Content.ContentType;
import com.lxp.domain.Course;
import com.lxp.domain.Course.CourseLevel;
import com.lxp.domain.CourseSection;
import com.lxp.domain.CourseSection.CourseSectionStatus;
import com.lxp.repository.ContentRepository;
import com.lxp.repository.CourseRepository;
import com.lxp.repository.CourseSectionRepository;
import java.util.List;


public class Service {

    private final CourseRepository courseRepository;
    private final CourseSectionRepository courseSectionRepository;
    private final ContentRepository contentRepository;

    public Service(CourseRepository courseRepository,
        CourseSectionRepository courseSectionRepository, ContentRepository contentRepository) {
        this.courseRepository = courseRepository;
        this.courseSectionRepository = courseSectionRepository;
        this.contentRepository = contentRepository;
    }

    // 선언

    public Course createCourse(String title, String description, Long courseId,
        CourseLevel courseLevel) {
        Course course = new Course(title, description, courseId, courseLevel);
        return courseRepository.save(course);
    }

    public CourseSection createCourseSection(Long courseId, String title) {
        courseRepository.findById(courseId)
            .orElseThrow(() -> new IllegalArgumentException(courseId + "id를 찾을 수 없습니다"));
        CourseSection courseSection = new CourseSection(courseId, title);
        return courseSectionRepository.save(courseSection);
    }

    public Content createContent(Long sectionId, String title, ContentType contentType) {
        courseSectionRepository.findById(sectionId)
            .orElseThrow(() -> new IllegalArgumentException(sectionId + "id를 찾을 수 없습니다"));
        Content content = new Content(sectionId, title, contentType);
        return contentRepository.save(content);
    }

    // 여기까지가 생성 저장
    public Course readCourse(Long courseId) {
        return courseRepository.findById(courseId)
            .orElseThrow(() -> new IllegalArgumentException(courseId + "강좌를 찾을 수 없음"));
    }

    public CourseSection readCourseSection(Long sectionId) {
        return courseSectionRepository.findById(sectionId)
            .orElseThrow(() -> new IllegalArgumentException(sectionId + "강좌를 찾을 수 없음"));
    }

    public Content readContent(Long contentId) {
        return contentRepository.findById(contentId)
            .orElseThrow(() -> new IllegalArgumentException(contentId + "강좌를 찾을 수 없음"));
    }

    // 여기까지가 단순 조회

    public void publishCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new IllegalArgumentException(courseId + "강좌를 찾을수 없음"));

        course.publish();
        courseRepository.update(course);

    }

    public void archiveCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new IllegalArgumentException(courseId + "강좌를 찾을 수 없음"));

        course.archived();
        courseRepository.update(course);
    }


    public void publishCourseSection(Long courseSectionId) {
        CourseSection courseSection = courseSectionRepository.findById(courseSectionId)
            .orElseThrow(() -> new IllegalArgumentException(courseSectionId + "강좌 찾을 수 없음"));

        courseSection.publish();
        courseSectionRepository.update(courseSection);
    }

    public void archivedCourseSection(Long courseSectionId) {
        CourseSection courseSection = courseSectionRepository.findById(courseSectionId)
            .orElseThrow(() -> new IllegalArgumentException(courseSectionId + "강좌 찾을 수 없음"));

        courseSection.archived();
        courseSectionRepository.update(courseSection);
    }

    public void hiddenContent(Long contentId) {
        Content content = contentRepository.findById(contentId)
            .orElseThrow(() -> new IllegalArgumentException(contentId + "강좌 찾을 수 없음"));

        content.hidden();
        contentRepository.update(content);
    }

    // 여기까지가 상태 변경

    public List<CourseSection> CourseSectionAll(Long courseId) {
        return courseSectionRepository.findAll(courseId);
    }

    public List<Content> ContentAll(Long sectionId) {
        return contentRepository.findAll(sectionId);
    }

    // 여기까지가 단체 조회

    public void updateCourse(Course course) {
        Course updtaeCourse = courseRepository.findById(course.getId())
            .orElseThrow(() -> new IllegalArgumentException(course.getId() + "강좌 찾을 수 없음"));

        updtaeCourse.updateShield(course.getTitle(), course.getDescription(),
            course.getCourseLevel());
        courseRepository.update(updtaeCourse);
    }

    public void updateCourseSection(CourseSection courseSection) {
        CourseSection courseS = courseSectionRepository.findById(courseSection.getId())
            .orElseThrow(() -> new IllegalArgumentException(courseSection.getId() + "강좌 찾을 수 없음"));
        if (courseS.getStatus() == CourseSectionStatus.ARCHIVED) {
            throw new IllegalArgumentException("운영 종료된 강좌는 변경할 수 없습니다");
        }
        courseS.updateShield(courseSection.getTitle());
        courseSectionRepository.update(courseS);
    }

    public void updateContent(Content content) {
        contentRepository.findById(content.getId())
            .orElseThrow(() -> new IllegalArgumentException(content.getId() + "강좌 찾을 수 없음"));
        contentRepository.update(content);
    }

    // 여기까지가 업데이트

    public void deleteCourse(Long courseId) {
        courseRepository.findById(courseId)
            .orElseThrow(() -> new IllegalArgumentException(courseId + "강좌 찾을 수 없음"));

        courseRepository.softDelete(courseId);
    }

    public void deleteCourseSection(Long courseSectionId) {
        courseSectionRepository.findById(courseSectionId)
            .orElseThrow(() -> new IllegalArgumentException(courseSectionId + "강좌 찾을 수 없음"));

        courseSectionRepository.softDelete(courseSectionId);
    }

    public void deleteContent(Long contentId) {
        contentRepository.findById(contentId)
            .orElseThrow(() -> new IllegalArgumentException(contentId + "강좌 찾을 수 없음"));

        contentRepository.softDelete(contentId);
    }

    // 여기까지가 삭제(소프트)

}
