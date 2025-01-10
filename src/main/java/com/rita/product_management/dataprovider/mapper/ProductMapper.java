package com.rita.product_management.dataprovider.mapper;

import com.rita.product_management.core.domain.Product;
import com.rita.product_management.dataprovider.database.entity.ProductEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ProductMapper {

    Product fromEntityToModel (ProductEntity productEntity);
    ProductEntity fromModelToEntity (Product product);

}
