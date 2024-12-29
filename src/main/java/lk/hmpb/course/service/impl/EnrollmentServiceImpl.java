package lk.hmpb.course.service.impl;

import jakarta.transaction.Transactional;
import lk.hmpb.course.entiry.Course;
import lk.hmpb.course.entiry.Enrollment;
import lk.hmpb.course.entiry.User;
import lk.hmpb.course.repository.CourseRepository;
import lk.hmpb.course.repository.EnrollmentRepository;
import lk.hmpb.course.repository.UserRepository;
import lk.hmpb.course.service.EnrollmentService;
import lk.hmpb.course.service.util.Transformer;
import lk.hmpb.course.to.CourseEnrollmentStatusResTo;
import lk.hmpb.course.to.EnrollmentDTO;
import lk.hmpb.course.to.EnrollmentResTo;
import lk.hmpb.course.to.EnrollmentStatusResponse;
import lk.hmpb.course.util.ApiResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final Transformer transformer;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository, UserRepository userRepository, CourseRepository courseRepository, Transformer transformer) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.transformer = transformer;
    }

    @Override
    public ApiResponse<List<EnrollmentResTo>> getEnrollmentsByStudentId(Long studentId) {
        List<Enrollment> enrollments = enrollmentRepository.findByUserId(studentId);
        if (enrollments.isEmpty()) {
            return new ApiResponse<>(null, false, "No enrollments found for this student");
        }
        List<EnrollmentResTo> response = enrollments.stream()
                .map(transformer::toEnrollmentResTo)
                .collect(Collectors.toList());
        return new ApiResponse<>(response, true, null);
    }

    @Override
    public ApiResponse<EnrollmentStatusResponse> getEnrollmentStatus(Long studentId, Long courseId) {
        Optional<Enrollment> enrollment = enrollmentRepository.findByUserIdAndCourseId(studentId, courseId);

        if (enrollment.isPresent()) {
            // If student is enrolled, create the response with 'enrolled' as true and include 'enrollmentId'
            EnrollmentStatusResponse response = new EnrollmentStatusResponse(true, enrollment.get().getId().toString());
            return new ApiResponse<>(response, true, null);
        }

        // If student is not enrolled, create the response with 'enrolled' as false and no 'enrollmentId'
        EnrollmentStatusResponse response = new EnrollmentStatusResponse(false, null);
        return new ApiResponse<>(response, true, null);
    }

    @Override
    public ApiResponse<EnrollmentResTo> createEnrollment(Long studentId, Long courseId) {
        Optional<User> user = userRepository.findById(studentId);
        Optional<Course> course = courseRepository.findById(courseId);

        if (user.isPresent() && course.isPresent()) {
            Optional<Enrollment> existingEnrollment = enrollmentRepository.findByUserIdAndCourseId(studentId, courseId);
            if (existingEnrollment.isPresent()) {
                return new ApiResponse<>(null, false, "Student is already enrolled in this course");
            }
            Enrollment enrollment = new Enrollment(null, user.get(), course.get());
            Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
            EnrollmentResTo response = transformer.toEnrollmentResTo(savedEnrollment);
            return new ApiResponse<EnrollmentResTo>(response, true, null);
        }
        return new ApiResponse<>(null, false, "Course or User not found");
    }

    @Override
    public ApiResponse<Void> deleteEnrollmentById(Long id) {
        if (enrollmentRepository.existsById(id)) {
            enrollmentRepository.deleteById(id);
            return new ApiResponse<>(null, true, null);
        }
        return new ApiResponse<>(null, false, "Enrollment not found");
    }

    @Override
    public ApiResponse<List<CourseEnrollmentStatusResTo>> getCoursesWithEnrollmentStatus(Long studentId) {
        List<Course> courses = courseRepository.findAll();
        List<Enrollment> enrollments = enrollmentRepository.findByUserId(studentId);
        List<Long> enrolledCourseIds = enrollments.stream()
                .map(e -> e.getCourse().getId())
                .collect(Collectors.toList());

        List<CourseEnrollmentStatusResTo> response = courses.stream()
                .map(course -> transformer.toCourseEnrollmentStatusResTo(course, enrolledCourseIds.contains(course.getId())))
                .collect(Collectors.toList());

        return new ApiResponse<>(response, true, null);
    }

}