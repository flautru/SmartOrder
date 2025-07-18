package com.fabien.smart_order.payment;

public interface PaymentMethod {
    String getLabel();

    boolean processPayment(double amount);

}
