package com.example.blogmanager.convert;

import com.example.blogmanager.model.DO.User;
import com.example.blogmanager.model.DTO.UserInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface UserInfoConvert {
    UserInfoConvert INSTANCE = Mappers.getMapper(UserInfoConvert.class);

    public UserInfoDTO userDoToUserInfoDTO(User user);
}
