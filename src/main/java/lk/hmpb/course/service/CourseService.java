package lk.hmpb.course.service;

import lk.hmpb.course.entiry.Course;
import lk.hmpb.course.to.CourseResTo;
import lk.hmpb.course.util.ApiResponse;

import java.util.List;

public interface CourseService {
    ApiResponse<List<CourseResTo>> getAllCourses();
    ApiResponse<CourseResTo> getCourseById(Long id);
    ApiResponse<CourseResTo> createCourse(Course course);
    ApiResponse<CourseResTo> updateCourse(Long id, Course course);
    ApiResponse<Void> deleteCourse(Long id);
}