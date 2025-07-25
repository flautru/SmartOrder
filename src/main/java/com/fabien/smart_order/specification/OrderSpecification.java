package com.fabien.smart_order.specification;

import com.fabien.smart_order.model.Order;

public interface OrderSpecification {
    boolean isSatisfiedBy(Order order);

    String getErrorMessage();

    String getSpecificationName();
}