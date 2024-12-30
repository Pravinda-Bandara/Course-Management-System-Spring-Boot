package lk.hmpb.course.service.util;

import lk.hmpb.course.entiry.Course;
import lk.hmpb.course.entiry.Enrollment;
import lk.hmpb.course.entiry.User;
import lk.hmpb.course.to.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class Transformer {

    private final ModelMapper mapper;

    public Transformer(ModelMapper mapper) {
        this.mapper = mapper;
    }

    // User
    public User toUserEntity(UserRegisterReqTo userRegisterReqTo) {
        return mapper.map(userRegisterReqTo, User.class);
    }

    public UserRegisterReqTo toUserRegisterTO(User user) {
        return mapper.map(user, UserRegisterReqTo.class);
    }

    public User toUserEntity(UserResponseTo userResponseTo) {
        return mapper.map(userResponseTo, User.class);
    }

    public UserResponseTo toUserResponseTO(User user) {
        UserResponseTo response = mapper.map(user, UserResponseTo.class);
        response.setToken(null);
        return response;
    }

    public AllUserResponseTo toAllUserResponseTO(User user) {
        AllUserResponseTo response = new AllUserResponseTo();

        // Manually set values from User to AllUserResponseTo
        response.set_id(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().name());
        response.setNumber(user.getNumber());

        return response;
    }

    // Course
    public CourseResTo toCourseResTo(Course course) {
        return mapper.map(course, CourseResTo.class);
    }

    // Course
    public CourseEnrollmentStatusResTo toCourseEnrollmentStatusResTo(Course course, boolean enrolled) {
        return new CourseEnrollmentStatusResTo(
                course.getId().toString(),
                course.getTitle(),
                course.getDescription(),
                course.getDuration(),
                course.getInstructor(),
                course.getInstructorNum(),
                enrolled
        );
    }
    // Enrollment
    public EnrollmentResTo toEnrollmentResTo(Enrollment enrollment) {
        EnrollmentResTo enrollmentResTo = mapper.map(enrollment, EnrollmentResTo.class);

        // Ensure studentId, studentEmail, and courseName are populated correctly
        enrollmentResTo.setStudentId(enrollment.getUser().getId());
        enrollmentResTo.setStudentEmail(enrollment.getUser().getEmail());
        enrollmentResTo.setCourseName(enrollment.getCourse().getTitle());

        return enrollmentResTo;

    }
}