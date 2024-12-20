package lk.hmpb.course.service.util;

import lk.hmpb.course.entiry.User;
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

    public User toUserEntity(UserRegisterReqTo userRegisterReqTo) {
        return mapper.map(userRegisterReqTo, User.class);
    }

    public UserRegisterReqTo toUserRegisterDTO(User user) {
        return mapper.map(user, UserRegisterReqTo.class);
    }

    public User toUserEntity(UserResponseTo userResponseTo) {
        return mapper.map(userResponseTo, User.class);
    }

    public UserResponseTo toUserResponseDTO(User user) {
        return mapper.map(user, UserResponseTo.class);
    }
}