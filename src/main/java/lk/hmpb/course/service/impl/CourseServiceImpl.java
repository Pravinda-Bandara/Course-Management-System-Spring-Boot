package lk.hmpb.course.service.impl;


import jakarta.transaction.Transactional;
import lk.hmpb.course.entiry.Course;
import lk.hmpb.course.repository.CourseRepository;
import lk.hmpb.course.repository.EnrollmentRepository;
import lk.hmpb.course.service.CourseService;
import lk.hmpb.course.service.util.Transformer;
import lk.hmpb.course.to.ResTO.CourseResTo;
import lk.hmpb.course.util.ApiResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final Transformer transformer;
    private final EnrollmentRepository enrollmentRepository;

    public CourseServiceImpl(CourseRepository courseRepository, Transformer transformer, EnrollmentRepository enrollmentRepository) {

        this.courseRepository = courseRepository;
        this.transformer = transformer;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public ApiResponse<List<CourseResTo>> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        List<CourseResTo> courseResTos = courses.stream()
                .map(transformer::toCourseResTo)
                .toList();
        return new ApiResponse<>(courseResTos, true, null);
    }

    @Override
    public ApiResponse<CourseResTo> getCourseById(Long id) {
        Optional<Course> course = courseRepository.findById(id);
        return course.map(value -> new ApiResponse<>(transformer.toCourseResTo(value), true, null))
                .orElseGet(() -> new ApiResponse<>(null, false, "Course not found"));
    }

    @Override
    public ApiResponse<CourseResTo> createCourse(Course course) {
        Course savedCourse = courseRepository.save(course);
        CourseResTo courseResTo = transformer.toCourseResTo(savedCourse);
        return new ApiResponse<>(courseResTo, true, null);
    }

    @Override
    public ApiResponse<CourseResTo> updateCourse(Long id, Course course) {
        Optional<Course> existingCourse = courseRepository.findById(id);
        if (existingCourse.isPresent()) {
            Course updatedCourse = existingCourse.get();
            updatedCourse.setTitle(course.getTitle());
            updatedCourse.setDescription(course.getDescription());
            updatedCourse.setDuration(course.getDuration());
            updatedCourse.setInstructor(course.getInstructor());
            updatedCourse.setInstructorNum(course.getInstructorNum());

            // Save the updated course
            courseRepository.save(updatedCourse);

            // Use the transformer to map to CourseResTo
            CourseResTo courseResTo = transformer.toCourseResTo(updatedCourse);

            return new ApiResponse<>(courseResTo, true, null);
        } else {
            return new ApiResponse<>(null, false, "Course not found");
        }
    }

    @Override
    @Transactional
    public ApiResponse<Void> deleteCourse(Long id) {

        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            course.getEnrollments().clear();
            courseRepository.delete(course);

            return new ApiResponse<>(null, true, "Course deleted successfully");
        } else {
            return new ApiResponse<>(null, false, "Course not found");
        }
    }
}