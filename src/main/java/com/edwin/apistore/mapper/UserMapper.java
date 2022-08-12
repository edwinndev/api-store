package com.edwin.apistore.mapper;

import com.edwin.apistore.dto.SignupRequestDto;
import com.edwin.apistore.dto.UserDto;
import com.edwin.apistore.entity.User;

/**
 * @author Edwin Vargas
 * @version 1.0
 */
public class UserMapper extends BaseMapper<User, UserDto> {

    @Override
    public User toEntity(UserDto dto) {
        return User.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .names(dto.getNames())
                .pLastname(dto.getPlastname())
                .mLastname(dto.getMlastname())
                .email(dto.getEmail().trim())
                .phone(dto.getPhone())
                .build();
    }

    @Override
    public UserDto toDto(User entity) {
        return UserDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .names(entity.getNames())
                .plastname(entity.getPLastname())
                .mlastname(entity.getMLastname())
                .email(entity.getEmail().trim())
                .phone(entity.getPhone())
                .build();
    }
    
    public User toUser (SignupRequestDto signup){
        return User.builder()
                .username(signup.getUsername())
                .names(signup.getNames())
                .pLastname(signup.getPlastname())
                .mLastname(signup.getMlastname())
                .email(signup.getEmail().trim())
                .phone(signup.getPhone())
                .password(signup.getPassword())
                .build();
    }
}