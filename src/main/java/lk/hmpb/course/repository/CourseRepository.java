package lk.hmpb.course.repository;

import lk.hmpb.course.entiry.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c.id AS courseId, c.title AS courseTitle, " +
            "CASE WHEN e.id IS NOT NULL THEN true ELSE false END AS isEnrolled " +
            "FROM Course c LEFT JOIN Enrollment e ON c.id = e.course.id AND e.user.id = :studentId")
    List<?> getCoursesWithEnrollmentStatus(Long studentId);
}