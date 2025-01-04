package com.rita.product_management.dataprovider.mapper;

import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.dataprovider.database.entity.UserEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder())
public interface UserMapper {

    User fromEntityToModel(UserEntity userEntity);

    UserEntity fromModelToEntity(User user);

}
