package com.slimanice.orderservice.utils;

import feign.Logger;
import org.springframework.context.annotation.Bean;

public class FeignLogger {
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
