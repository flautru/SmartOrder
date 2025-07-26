package com.fabien.smart_order.util;

import com.fabien.smart_order.model.Order;
import com.fabien.smart_order.model.OrderItem;
import com.fabien.smart_order.model.Product;
import java.util.List;

public class TestDataBuilder {

    private static final String DEFAULT_DELIVERY = "Colissimo";
    private static final String DEFAULT_PAYMENT = "CB";

    public static Product createProduct(final Long id, final String name, final double price, final String type) {
        return new Product(id, name, price, type);
    }

    public static Product createProduct(final String name, final double price) {
        return createProduct(1L, name, price, "Test");
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
            createProduct(3L, "ChaiseTest", 45.99, "MeubleTest"),
            createProduct(4L, "TapisTest", 24.99, "DécorationTest")
        );
    }

    public static List<OrderItem> createStandardOrderItems() {
        return List.of(
            createOrderItem(1L, createProduct(1L, "LaptopTest", 599.99, "InfoTest"), 599.99, 2),
            createOrderItem(2L, createProduct(2L, "SourisTest", 49.99, "InfoTest"), 49.99, 5)
        );
    }

    public static List<OrderItem> createEmptyOrderItems() {
        return List.of();
    }

    public static Order createOrder(final List<Product> products, final String delivery, final String payment) {
        final List<OrderItem> orderItems = products.stream()
            .map(product -> new OrderItem(null, null, product, product.getPrice(), 2))
            .toList();

        return Order.builder()
            .withDelivery(delivery)
            .withPayment(payment)
            .withOrderItems(orderItems)
            .build();
    }

    public static Order createStandardOrder() {
        return createOrder(createStandardProducts(), DEFAULT_DELIVERY, DEFAULT_PAYMENT);
    }

    public static Order createOrderWithAmount(final double totalAmount) {
        return Order.builder()
            .withDelivery(DEFAULT_DELIVERY)
            .withPayment(DEFAULT_PAYMENT)
            .withTotalAmount(totalAmount)
            .withOrderItems(createEmptyOrderItems())
            .build();
    }

    public static Order createStandardOrderWithDelivery(final String delivery) {
        return createOrder(createStandardProducts(), delivery, DEFAULT_PAYMENT);
    }

    public static Order createStandardOrderWithId(final Long id) {
        final Order order = createStandardOrder();
        order.setId(id);
        return order;
    }

    public static Order createOrderWithIdAndAmount(final Long id, final double totalAmount) {
        final Order order = createStandardOrder();
        order.setId(id);
        order.setTotalAmount(totalAmount);
        return order;
    }

    public static List<Order> createOrderList() {
        return List.of(
            createStandardOrder(),
            createOrder(createFurnitureProducts(), "Chronopost", "Paypal")
        );
    }
}
