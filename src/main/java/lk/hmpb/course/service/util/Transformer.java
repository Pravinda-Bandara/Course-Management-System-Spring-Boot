package lk.hmpb.course.service.util;

import lk.hmpb.course.entiry.User;
import lk.hmpb.course.to.UserRegisterReqTo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class Transformer {

    private final ModelMapper mapper;

    public Transformer(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public User fromUserTO(UserRegisterReqTo userTO){
        return mapper.map(userTO, User.class);
    }

    public UserRegisterReqTo toUserTO(User user){
        return mapper.map(user, UserRegisterReqTo.class);
    }
}