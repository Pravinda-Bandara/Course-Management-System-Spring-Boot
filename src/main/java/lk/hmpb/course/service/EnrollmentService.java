package lk.hmpb.course.service;

import lk.hmpb.course.entiry.Enrollment;
import lk.hmpb.course.to.CourseEnrollmentStatusResTo;
import lk.hmpb.course.to.EnrollmentDTO;
import lk.hmpb.course.to.EnrollmentResTo;
import lk.hmpb.course.to.EnrollmentStatusResponse;
import lk.hmpb.course.util.ApiResponse;

import java.util.List;
import java.util.Optional;

public interface EnrollmentService {

    ApiResponse<List<EnrollmentResTo>> getEnrollmentsByStudentId(Long studentId);
    ApiResponse<EnrollmentStatusResponse> getEnrollmentStatus(Long studentId, Long courseId);
    ApiResponse<EnrollmentResTo> createEnrollment(Long studentId, Long courseId);
    ApiResponse<Void> deleteEnrollmentById(Long id);
    ApiResponse<List<CourseEnrollmentStatusResTo>> getCoursesWithEnrollmentStatus(Long studentId);
}
