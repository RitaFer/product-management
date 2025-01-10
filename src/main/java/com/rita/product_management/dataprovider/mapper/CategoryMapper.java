package com.rita.product_management.dataprovider.mapper;

import com.rita.product_management.core.domain.Category;
import com.rita.product_management.dataprovider.database.entity.CategoryEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CategoryMapper {

    Category fromEntityToModel (CategoryEntity categoryEntity);
    CategoryEntity fromModelToEntity (Category category);

}
