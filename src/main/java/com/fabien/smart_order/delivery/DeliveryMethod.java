package com.fabien.smart_order.delivery;

public interface DeliveryMethod {
    String getLabel();

    double getPrice();

    void deliver(String orderId);
}
