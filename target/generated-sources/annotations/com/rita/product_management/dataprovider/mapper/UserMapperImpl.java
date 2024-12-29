package com.rita.product_management.dataprovider.mapper;

import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.dataprovider.database.entity.UserEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-28T21:44:26-0400",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User fromEntityToModel(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        User user = new User();

        user.setId( userEntity.getId() );
        user.setUsername( userEntity.getUsername() );
        user.setPassword( userEntity.getPassword() );
        user.setRole( userEntity.getRole() );
        user.setActive( userEntity.isActive() );
        user.setName( userEntity.getName() );
        user.setEmail( userEntity.getEmail() );

        return user;
    }

    @Override
    public UserEntity fromModelToEntity(User user) {
        if ( user == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setId( user.getId() );
        userEntity.setUsername( user.getUsername() );
        userEntity.setPassword( user.getPassword() );
        userEntity.setRole( user.getRole() );
        userEntity.setActive( user.isActive() );
        userEntity.setName( user.getName() );
        userEntity.setEmail( user.getEmail() );

        return userEntity;
    }
}
