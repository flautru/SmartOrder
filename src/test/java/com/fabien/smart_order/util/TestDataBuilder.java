package com.fabien.smart_order.util;

import com.fabien.smart_order.model.Order;
import com.fabien.smart_order.model.OrderItem;
import com.fabien.smart_order.model.Product;
import java.util.List;

public class TestDataBuilder {

    public static Order createOrderWithAmount(final double totalAmount) {
        return Order.builder()
            .withDelivery("Colissimo")
            .withPayment("CB")
            .withTotalAmount(totalAmount)
            .withOrderItems(List.of())
            .build();
    }

    public static Order createOrderWithProducts(final List<Product> products, final String delivery,
        final String payment) {
        final List<OrderItem> orderItems = products.stream()
            .map(product -> new OrderItem(null, null, product, product.getPrice(), 1))
            .toList();

        return Order.builder()
            .withDelivery(delivery)
            .withPayment(payment)
            .withOrderItems(orderItems)
            .build();
    }

    public static Product createProduct(final Long id, final String name, final double price, final String type) {
        return new Product(id, name, price, type);
    }

    public static Product createProduct(final String name, final double price) {
        return new Product(1L, name, price, "Test");
    }

    public static OrderItem createOrderItem(final Long id, final Product product, final double unitPrice,
        final int quantity) {
        final OrderItem item = new OrderItem();
        item.setId(id);
        item.setProduct(product);
        item.setUnitPrice(unitPrice);
        item.setQuantity(quantity);
        return item;
    }

    public static List<Product> createStandardProducts() {
        return List.of(
            createProduct(1L, "LaptopTest", 599.99, "InfoTest"),
            createProduct(2L, "SourisTest", 49.99, "InfoTest")
        );
    }

    public static List<Product> createFurnitureProducts() {
        return List.of(
            createProduct(null, "ChaiseTest", 45.99, "MeubleTest"),
            createProduct(null, "TapisTest", 24.99, "DécorationTest")
        );
    }

    public static Order createStandardOrder() {
        return createOrderWithProducts(createStandardProducts(), "Colissimo", "CB");
    }

    public static Order createFurnitureOrder() {
        return createOrderWithProducts(createFurnitureProducts(), "Chronopost", "Paypal");
    }

    public static List<Order> createOrderList() {
        return List.of(createStandardOrder(), createFurnitureOrder());
    }

    public static Order createStandardOrderWithDelivery(final String delivery) {
        return createOrderWithProducts(createStandardProducts(), delivery, "CB");
    }

    public static Order createStandardOrderWithPayment(final String payment) {
        return createOrderWithProducts(createStandardProducts(), "Colissimo", payment);
    }
}
