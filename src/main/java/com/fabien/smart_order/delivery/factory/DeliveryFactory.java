package com.fabien.smart_order.delivery.factory;

import com.fabien.smart_order.delivery.DeliveryMethod;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class DeliveryFactory {

    private final Map<String, DeliveryMethod> deliveryMethods;

    public DeliveryFactory(final List<DeliveryMethod> methods) {
        this.deliveryMethods = methods.stream()
            .collect(Collectors.toMap(DeliveryMethod::getLabel, Function.identity()));
    }

    public DeliveryMethod getDeliveryMethod(final String type) {
        final DeliveryMethod method = deliveryMethods.get(type);
        if (method == null) {
            throw new IllegalArgumentException("Unknown delivery method: " + type);
        }
        return method;
    }

    public List<DeliveryMethod> getAllDeliveryMethods() {
        return List.copyOf(deliveryMethods.values());
    }
}
