package com.slimanice.orderservice.web;

import com.slimanice.orderservice.utils.ConsulConfig;
import com.slimanice.orderservice.utils.VaultConfig;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
public class ConfigRestController {
    private ConsulConfig consulConfig;
    private VaultConfig vaultConfig;

    @GetMapping("/myConfig")
    public Map<String, Object> myConfig() {
        return Map.of("consulConfig", consulConfig, "vaultConfig", vaultConfig);
    }
}
