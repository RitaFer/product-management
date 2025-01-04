package com.rita.product_management.dataprovider.mapper;

import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.dataprovider.database.entity.UserEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-03T20:12:04-0400",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User fromEntityToModel(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( userEntity.getId() );
        user.active( userEntity.getActive() );
        user.name( userEntity.getName() );
        user.username( userEntity.getUsername() );
        user.email( userEntity.getEmail() );
        user.password( userEntity.getPassword() );
        user.role( userEntity.getRole() );
        user.createdAt( userEntity.getCreatedAt() );
        user.updatedAt( userEntity.getUpdatedAt() );

        return user.build();
    }

    @Override
    public UserEntity fromModelToEntity(User user) {
        if ( user == null ) {
            return null;
        }

        UserEntity.UserEntityBuilder userEntity = UserEntity.builder();

        userEntity.id( user.getId() );
        userEntity.active( user.getActive() );
        userEntity.name( user.getName() );
        userEntity.username( user.getUsername() );
        userEntity.email( user.getEmail() );
        userEntity.password( user.getPassword() );
        userEntity.role( user.getRole() );
        userEntity.createdAt( user.getCreatedAt() );
        userEntity.updatedAt( user.getUpdatedAt() );

        return userEntity.build();
    }
}
