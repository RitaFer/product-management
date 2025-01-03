package com.rita.product_management.dataprovider.mapper;

import com.rita.product_management.core.domain.enums.UserType;
import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.dataprovider.database.entity.UserEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-03T10:48:00-0400",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User fromEntityToModel(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        String name = null;
        String email = null;
        UserType role = null;

        User user = new User( name, email, role );

        return user;
    }

    @Override
    public UserEntity fromModelToEntity(User user) {
        if ( user == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        return userEntity;
    }
}
