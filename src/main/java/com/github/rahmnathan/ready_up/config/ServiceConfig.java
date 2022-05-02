package com.github.rahmnathan.ready_up.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "service")
public class ServiceConfig {
    private boolean forceConvert;
}
