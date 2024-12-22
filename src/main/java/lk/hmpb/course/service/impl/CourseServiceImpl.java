package lk.hmpb.course.service.impl;


import jakarta.transaction.Transactional;
import lk.hmpb.course.entiry.Course;
import lk.hmpb.course.repository.CourseRepository;
import lk.hmpb.course.service.CourseService;
import lk.hmpb.course.util.ApiResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public ApiResponse<List<Course>> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return new ApiResponse<>(courses, true, null);
    }

    @Override
    public ApiResponse<Course> getCourseById(Long id) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isPresent()) {
            return new ApiResponse<>( course.get(),true, null);
        } else {
            return new ApiResponse<>(null, false, "Course not found");
        }
    }

    @Override
    public ApiResponse<Course> createCourse(Course course) {
        Course savedCourse = courseRepository.save(course);
        return new ApiResponse<>(savedCourse,true,  null);
    }

    @Override
    public ApiResponse<Course> updateCourse(Long id, Course course) {
        Optional<Course> existingCourse = courseRepository.findById(id);
        if (existingCourse.isPresent()) {
            Course updatedCourse = existingCourse.get();
            updatedCourse.setTitle(course.getTitle());
            updatedCourse.setDescription(course.getDescription());
            updatedCourse.setDuration(course.getDuration());
            updatedCourse.setInstructor(course.getInstructor());
            updatedCourse.setInstructorNum(course.getInstructorNum());
            courseRepository.save(updatedCourse);
            return new ApiResponse<>(updatedCourse,true,  null);
        } else {
            return new ApiResponse<>(null, false, "Course not found");
        }
    }

    @Override
    public ApiResponse<Void> deleteCourse(Long id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            return new ApiResponse<>(null,true,  "Course deleted successfully");
        } else {
            return new ApiResponse<>(null, false, "Course not found");
        }
    }
}