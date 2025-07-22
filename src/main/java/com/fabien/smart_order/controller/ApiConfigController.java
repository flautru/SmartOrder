package com.fabien.smart_order.controller;

import com.fabien.smart_order.config.AppConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/config")
public class ApiConfigController {

    AppConfig config = AppConfig.getInstance();

    @GetMapping
    public ResponseEntity<AppConfig> getConfig() {
        return ResponseEntity.ok(config);
    }
}
