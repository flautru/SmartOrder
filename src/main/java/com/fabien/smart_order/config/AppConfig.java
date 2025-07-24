package com.fabien.smart_order.config;

import lombok.Getter;
import lombok.Setter;

@Getter
public class AppConfig {

    private static AppConfig instance;
    private final String applicationName;
    private final double discount_threshold;
    private final double discount_rate;
    @Setter
    private double defaultVatRate;
    @Setter
    private boolean demoMode;

    private AppConfig() {
        this.applicationName = "SmartOrder";
        this.defaultVatRate = 0.2;
        this.demoMode = false;
        this.discount_threshold = 150.0;
        this.discount_rate = 0.10;
    }

    public static AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }
}
