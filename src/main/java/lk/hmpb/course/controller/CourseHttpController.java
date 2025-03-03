package lk.hmpb.course.controller;

import jakarta.validation.Valid;
import lk.hmpb.course.entiry.Course;
import lk.hmpb.course.service.CourseService;
import lk.hmpb.course.to.ResTO.CourseResTo;
import lk.hmpb.course.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin
public class CourseHttpController {

    private final CourseService courseService;

    public CourseHttpController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CourseResTo>>> getAllCourses() {
        ApiResponse<List<CourseResTo>> response = courseService.getAllCourses();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResTo>> getCourseById(@PathVariable Long id) {
        ApiResponse<CourseResTo> response = courseService.getCourseById(id);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CourseResTo>> createCourse(@Valid @RequestBody Course course) {
        ApiResponse<CourseResTo> response = courseService.createCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResTo>> updateCourse(@PathVariable Long id, @Valid @RequestBody Course course) {
        ApiResponse<CourseResTo> response = courseService.updateCourse(id, course);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCourse(@PathVariable Long id) {
        ApiResponse<Void> response = courseService.deleteCourse(id);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}