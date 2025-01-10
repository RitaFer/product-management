package com.rita.product_management.dataprovider.database.specification;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class BaseSpecification<T, P> {
    public abstract Specification<T> getFilter(P filter);

    protected String containsLowerCase(String searchField) {
        String wildcard = "%";
        return wildcard + searchField.toLowerCase() + wildcard;
    }

    protected Specification<T> attributeContains(String attribute, String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }
            return cb.like(cb.lower(root.get(attribute)), containsLowerCase(value));
        };
    }

    protected <R> Specification<T> attributeEquals(Join<?, ?> join, String attribute, R value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }
            return cb.equal(join.get(attribute).as(value.getClass()), value);
        };
    }

    protected <R> Specification<T> attributeEquals(String attribute, R value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }
            return cb.equal(root.get(attribute).as(value.getClass()), value);
        };
    }

    protected Specification<T> attributeBetweenDates(
            String attribute, LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, cb) -> {
            if (startDate == null || endDate == null) {
                return null;
            }
            return cb.between(root.get(attribute), startDate, endDate);
        };
    }

    protected Specification<T> attributeBetweenValues(
            String attribute, Long start, Long end) {
        return (root, query, cb) -> {
            if (start == null || end == null) {
                return null;
            }
            return cb.between(root.get(attribute), start, end);
        };
    }

    protected Specification<T> attributeBetweenValues(
            String attribute, BigDecimal start, BigDecimal end) {
        return (root, query, cb) -> {
            if (start == null || end == null) {
                return null;
            }
            return cb.between(root.get(attribute), start, end);
        };
    }

    protected Specification<T> attributeBetweenValues(
            String attribute, Double start, Double end) {
        return (root, query, cb) -> {
            if (start == null || end == null) {
                return null;
            }
            return cb.between(root.get(attribute), start, end);
        };
    }


}

