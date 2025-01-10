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

            return Specification.where(attributeEquals(Field.id.name(), filter.getId()))
                    .and(attributeEquals(Field.isActive.name(), filter.getIsActive()))
                    .and(attributeContains(Field.name.name(), filter.getName()))
                    .and(attributeEquals(Field.active.name(), filter.getActive()))
                    .and(attributeEquals(Field.sku.name(), filter.getSku()))
                    .and(attributeBetweenValues(Field.costValue.name(), filter.getCostValueInitial(), filter.getCostValueFinal()))
                    .and(attributeBetweenValues(Field.icms.name(), filter.getIcmsInitial(), filter.getIcmsFinal()))
                    .and(attributeBetweenValues(Field.saleValue.name(), filter.getSaleValueInitial(), filter.getSaleValueFinal()))
                    .and(attributeBetweenValues(Field.quantityInStock.name(), filter.getQuantityInStockInitial(), filter.getQuantityInStockFinal()))
                    .and(attributeEquals(userCreatedProductJoin, Field.id.name(), filter.getCreatedBy()))
                    .and(attributeBetweenDates(Field.createdAt.name(), filter.getCreatedAtInitial(), filter.getCreatedAtFinal()))
                    .and(attributeEquals(userUpdatedProductJoin, Field.id.name(), filter.getUpdatedBy()))
                    .and(attributeBetweenDates(Field.updatedAt.name(), filter.getUpdatedAtInitial(), filter.getUpdatedAtFinal()))
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
