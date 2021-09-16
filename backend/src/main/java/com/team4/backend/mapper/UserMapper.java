package com.team4.backend.mapper;

import com.team4.backend.dto.UserDto;
import com.team4.backend.model.User;
import org.modelmapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    protected ModelMapper modelMapper;

    protected final TypeMap<User, UserDto> userToUserDtoBaseTypeMap;
    protected final TypeMap<UserDto, User> userDtoToUserBaseTypeMap;

//    Converter<UserDto, User> dtoToEntity = new AbstractConverter<UserDto, User>() {
//        @Override
//        protected User convert(UserDto userDto) {
//            return modelMapper.map(userDto, User.class);
//        }
//    };
//
//    Converter<User, UserDto> entityToDto = new AbstractConverter<User, UserDto>() {
//        @Override
//        protected UserDto convert(User user) {
//            return modelMapper.map(user, UserDto.class);
//        }
//    };

    public UserMapper() {

        userToUserDtoBaseTypeMap = this.modelMapper.addMappings(new PropertyMap<User, UserDto>() {
            @Override
            protected void configure() {
                skip(source.getPassword());
            }
        });

        userDtoToUserBaseTypeMap = this.modelMapper.createTypeMap(UserDto.class, User.class);
    }

//    public User dtoToEntity(UserDto userDto) {
//        return modelMapper.map(userDto, User.class);
//    }
//
//    public UserDto dtoToEntity(User user) {
//        return modelMapper.map(user, UserDto.class);
//    }


}
