package lk.hmpb.course.service;

import lk.hmpb.course.to.ResTO.CourseEnrollmentStatusResTo;
import lk.hmpb.course.to.ResTO.EnrollmentResTo;
import lk.hmpb.course.to.ResTO.EnrollmentStatusResponse;
import lk.hmpb.course.util.ApiResponse;

import java.util.List;

public interface EnrollmentService {

    ApiResponse<List<EnrollmentResTo>> getEnrollmentsByStudentId(Long studentId);
    ApiResponse<EnrollmentStatusResponse> getEnrollmentStatus(Long studentId, Long courseId);
    ApiResponse<EnrollmentResTo> createEnrollment(Long studentId, Long courseId);
    ApiResponse<Void> deleteEnrollmentById(Long id);
    ApiResponse<List<CourseEnrollmentStatusResTo>> getCoursesWithEnrollmentStatus(Long studentId);
}
