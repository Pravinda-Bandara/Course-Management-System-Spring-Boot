package lk.hmpb.course.service;

import lk.hmpb.course.entiry.Course;
import lk.hmpb.course.util.ApiResponse;

import java.util.List;

public interface CourseService {
    ApiResponse<List<Course>> getAllCourses();
    ApiResponse<Course> getCourseById(Long id);
    ApiResponse<Course> createCourse(Course course);
    ApiResponse<Course> updateCourse(Long id, Course course);
    ApiResponse<Void> deleteCourse(Long id);
}