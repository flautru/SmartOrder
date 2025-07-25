package com.fabien.smart_order.controller;

import com.fabien.smart_order.dto.OrderRequest;
import com.fabien.smart_order.dto.OrderResponse;
import com.fabien.smart_order.mapper.OrderMapper;
import com.fabien.smart_order.model.Order;
import com.fabien.smart_order.service.Order.OrderService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RequestMapping("/api/orders")
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        final List<Order> orders = orderService.getAllOrder();
        final List<OrderResponse> orderResponses = orders.stream().map(OrderMapper::toDto).toList();

        return ResponseEntity.ok(orderResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        final Order order = orderService.getOrderById(id);

        final OrderResponse orderResponse = OrderMapper.toDto(order);

        return ResponseEntity.ok(orderResponse);
    }

    @PostMapping()
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {

        final Order order = orderService.createOrder(OrderMapper.toEntity(orderRequest));
        final OrderResponse orderResponse = OrderMapper.toDto(order);

        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(order.getId())
            .toUri();

        return ResponseEntity.created(location).body(orderResponse);
    }

    @PostMapping("/{id}/clone")
    public ResponseEntity<OrderResponse> cloneOrder(@PathVariable Long id) {
        final Order order = orderService.getOrderById(id);

        final Order clonedOrder = orderService.cloneOrderById(id);
        final OrderResponse clonedOrderResponse = OrderMapper.toDto(clonedOrder);

        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(clonedOrder.getId())
            .toUri();

        return ResponseEntity.created(location).body(clonedOrderResponse);
    }

}
