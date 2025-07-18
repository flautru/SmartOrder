package com.fabien.smart_order.config;

import lombok.Getter;
import lombok.Setter;

@Getter
public class AppConfig {

    private static AppConfig instance;
    private final String applicationName;
    @Setter
    private double defaultVatRate;
    @Setter
    private boolean demoMode;

    private AppConfig() {
        this.applicationName = "SmartOrder";
        this.defaultVatRate = 0.2;
        this.demoMode = false;
    }

    public static AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }
}
