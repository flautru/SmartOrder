package com.fabien.smart_order.calculation;

public enum CalculationType {
    STANDARD("STANDARD"),
    DISCOUNT("DISCOUNT");

    private String name;

    CalculationType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
