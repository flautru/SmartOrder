package com.fabien.smart_order.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Table(name = "orders")
@Entity
@NoArgsConstructor
@AllArgsConstructor
//@Builder avec Lombok pour faire un builder sans code supplémentaire
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();
    private String delivery;
    private String payment;
    private double totalAmount;

    private static OrderBuilder builder() {
        return new OrderBuilder();
    }

    public void addItem(final Product product, final int quantity) {
        final OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setOrder(this);
        items.add(item);
    }

    public Order cloneWithBuilder() {

        return Order.builder()
            .withDelivery(this.delivery)
            .withPayment(this.payment)
            .withOrderItems(cloneOrderItems(this.items))
            .withTotalAmount(this.totalAmount)
            .build();
    }

    private List<OrderItem> cloneOrderItems(final List<OrderItem> orderItems) {
        final List<OrderItem> cloned = new ArrayList<>();
        for (final OrderItem item : orderItems) {
            cloned.add(item.cloneWithoutId());
        }
        return cloned;
    }

    public static class OrderBuilder {
        private List<OrderItem> orderItems;
        private String delivery;
        private String payment;
        private double totalAmount;

        public OrderBuilder withOrderItems(final List<OrderItem> orderItems) {
            if (orderItems != null) {
                this.orderItems = orderItems;
            }
            return this;
        }

        public OrderBuilder withDelivery(final String delivery) {
            if (delivery != null) {
                this.delivery = delivery;
            }
            return this;
        }

        public OrderBuilder withPayment(final String payment) {
            if (payment != null) {
                this.payment = payment;
            }
            return this;
        }

        public OrderBuilder withTotalAmount(final double total) {
            this.totalAmount = total;
            return this;
        }

        public Order build() {
            final Order order = new Order();
            order.setItems(orderItems);
            order.setDelivery(delivery);
            order.setPayment(payment);

            if (orderItems != null) {
                for (OrderItem item : orderItems) {
                    item.setOrder(order);
                }
            }

            return order;
        }
    }
}
