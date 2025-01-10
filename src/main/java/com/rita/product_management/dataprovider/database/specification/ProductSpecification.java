package com.rita.product_management.dataprovider.database.specification;

import com.rita.product_management.dataprovider.database.entity.CategoryEntity;
import com.rita.product_management.dataprovider.database.entity.ProductEntity;
import com.rita.product_management.dataprovider.database.entity.UserEntity;
import com.rita.product_management.entrypoint.api.dto.filters.ProductFilter;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ProductSpecification extends BaseSpecification<ProductEntity, ProductFilter> {

    public Join<UserEntity, ProductEntity> userCreatedProductJoin;
    public Join<UserEntity, ProductEntity> userUpdatedProductJoin;
    public Join<CategoryEntity, ProductEntity> categoryProductJoin;

    @Override
    public Specification<ProductEntity> getFilter(ProductFilter filter) {
        return (root, query, cb) -> {
            userCreatedProductJoin = root.join(Field.createdBy.name(), JoinType.LEFT);
            userUpdatedProductJoin = root.join(Field.updatedBy.name(), JoinType.LEFT);
            categoryProductJoin = root.join(Field.category.name(), JoinType.LEFT);

            return Specification.where(attributeEquals(Field.id.name(), filter.id()))
                    .and(attributeEquals(Field.isActive.name(), filter.isActive()))
                    .and(attributeEquals(Field.name.name(), filter.name()))
                    .and(attributeEquals(Field.active.name(), filter.active()))
                    .and(attributeEquals(Field.sku.name(), filter.active()))
                    .and(attributeBetweenValues(Field.costValue.name(), filter.costValueInitial(), filter.costValueFinal()))
                    .and(attributeBetweenValues(Field.icms.name(), filter.icmsInitial(), filter.icmsFinal()))
                    .and(attributeBetweenValues(Field.saleValue.name(), filter.saleValueInitial(), filter.saleValueFinal()))
                    .and(attributeBetweenValues(Field.quantityInStock.name(), filter.quantityInStockInitial(), filter.quantityInStockFinal()))
                    .and(attributeEquals(userCreatedProductJoin, Field.id.name(), filter.createdBy()))
                    .and(attributeBetweenDates(Field.createdAt.name(), filter.createdAtInitial(), filter.createdAtFinal()))
                    .and(attributeEquals(userUpdatedProductJoin, Field.id.name(), filter.updatedBy()))
                    .and(attributeBetweenDates(Field.updatedAt.name(), filter.updatedAtInitial(), filter.updatedAtFinal()))
                    .toPredicate(root, query, cb);
        };
    }

    private enum Field {
        id,
        isActive,
        name,
        active,
        sku,
        category,
        costValue,
        icms,
        saleValue,
        quantityInStock,
        createdBy,
        createdAt,
        updatedBy,
        updatedAt
    }

}
