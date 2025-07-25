package com.fabien.smart_order.specification.impl;

import com.fabien.smart_order.config.AppConfig;
import com.fabien.smart_order.model.Order;
import com.fabien.smart_order.specification.OrderSpecification;
import org.springframework.stereotype.Component;

@Component
public class MinimumAmountSpec implements OrderSpecification {

    AppConfig config = AppConfig.getInstance();
   
    @Override
    public boolean isSatisfiedBy(Order order) {
        return order != null && order.getTotalAmount() >= config.getMinimumOrderAmount();
    }

    @Override
    public String getErrorMessage() {
        return "Le montant de la commande doit être d'au moins " + config.getMinimumOrderAmount() + "€";
    }

    @Override
    public String getSpecificationName() {
        return "MINIMUM_AMOUNT";
    }
}