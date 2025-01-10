package com.rita.product_management.dataprovider.database.repository;

import com.rita.product_management.dataprovider.database.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {
}
