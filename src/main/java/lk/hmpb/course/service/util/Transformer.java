package lk.hmpb.course.service.util;

import lk.hmpb.course.entiry.Course;
import lk.hmpb.course.entiry.User;
import lk.hmpb.course.to.CourseResTo;
import lk.hmpb.course.to.UserRegisterReqTo;
import lk.hmpb.course.to.UserResponseTo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class Transformer {

    private final ModelMapper mapper;

    public Transformer(ModelMapper mapper) {
        this.mapper = mapper;
    }
    //user

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
        return mapper.map(user, UserResponseTo.class);
    }

    //course
    public CourseResTo toCourseResTo(Course course) {
        return mapper.map(course, CourseResTo.class);
    }
}