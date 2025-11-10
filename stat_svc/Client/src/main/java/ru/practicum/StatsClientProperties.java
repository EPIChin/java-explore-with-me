package ru.practicum;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "stats.client")
public class StatsClientProperties {
    private String baseUrl = "http://localhost:8080";
    private boolean enabled = true;
}