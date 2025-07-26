package com.fabien.smart_order.specification.impl;

import com.fabien.smart_order.model.Order;
import com.fabien.smart_order.specification.OrderSpecification;

public class NotEmptySpec implements OrderSpecification {
    @Override
    public boolean isSatisfiedBy(final Order order) {
        return order.getItems() != null
            && !order.getItems().isEmpty();
    }

    @Override
    public String getErrorMessage() {
        return "Une commande ne peut être vide";
    }

    @Override
    public String getSpecificationName() {
        return "NOT_EMPTY";
    }
}
