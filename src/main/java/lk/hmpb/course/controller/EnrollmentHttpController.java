package lk.hmpb.course.controller;


import lk.hmpb.course.service.EnrollmentService;
import lk.hmpb.course.to.ResTO.CourseEnrollmentStatusResTo;
import lk.hmpb.course.to.ReqTO.EnrollmentRequestTo;
import lk.hmpb.course.to.ResTO.EnrollmentResTo;
import lk.hmpb.course.to.ResTO.EnrollmentStatusResponse;
import lk.hmpb.course.util.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@CrossOrigin
public class EnrollmentHttpController {

    private final EnrollmentService enrollmentService;

    public EnrollmentHttpController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/{studentId}")
    public ApiResponse<?> getEnrollmentsByStudentId(@PathVariable Long studentId) {
        return enrollmentService.getEnrollmentsByStudentId(studentId);
    }

    @GetMapping("/status")
    public ApiResponse<EnrollmentStatusResponse> getEnrollmentStatus(@RequestParam Long studentId, @RequestParam Long courseId) {
        return enrollmentService.getEnrollmentStatus(studentId, courseId);
    }

    @PostMapping
    public ApiResponse<EnrollmentResTo> createEnrollment(@RequestBody EnrollmentRequestTo enrollmentRequestTo) {
        Long studentId = enrollmentRequestTo.getStudentId();
        Long courseId = enrollmentRequestTo.getCourseId();

        // Call the service method and return its response
        return enrollmentService.createEnrollment(studentId, courseId);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteEnrollmentById(@PathVariable Long id) {
        return enrollmentService.deleteEnrollmentById(id);
    }

    @GetMapping("/detailed/{studentId}")
    public ApiResponse<List<CourseEnrollmentStatusResTo>> getCoursesWithEnrollmentStatus(@PathVariable Long studentId) {
        return enrollmentService.getCoursesWithEnrollmentStatus(studentId);
    }
}
