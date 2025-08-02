package com.proper.classes.service.external;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class ActivityCostClient {
    public Mono<Double> getActivityCost(String activityName, int humanAge) {
        // Simulated cost logic (external call stub)
        double base = switch (activityName.toLowerCase()) {
            case "running" -> 10.0;
            case "swimming" -> 15.0;
            default -> 5.0;
        };
        double ageFactor = (humanAge < 30) ? 1.0 : 1.5;
        return Mono.just(base * ageFactor).delayElement(Duration.ofMillis(100));
    }
}
